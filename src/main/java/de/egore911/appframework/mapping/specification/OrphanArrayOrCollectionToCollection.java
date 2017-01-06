/*
 * Orika - simpler, better and faster Java bean mapping
 *
 * Copyright (C) 2011-2013 Orika authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.egore911.appframework.mapping.specification;

import static java.lang.String.format;
import static de.egore911.appframework.mapping.specification.AnnotationUtil.getIdProperty;
import static ma.glasnost.orika.impl.generator.SourceCodeContext.append;
import static ma.glasnost.orika.impl.generator.SourceCodeContext.statement;

import ma.glasnost.orika.MappingException;
import ma.glasnost.orika.impl.generator.MultiOccurrenceVariableRef;
import ma.glasnost.orika.impl.generator.SourceCodeContext;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.impl.generator.specification.ArrayOrCollectionToCollection;
import ma.glasnost.orika.metadata.FieldMap;
import ma.glasnost.orika.metadata.Type;

/**
 * ArrayOrCollectionToCollection handles mapping of an Array or Collection
 * to a Collection, while taking orphan removal of hibernate into account.
 *
 */
public class OrphanArrayOrCollectionToCollection extends ArrayOrCollectionToCollection {

    @Override
    public String generateMappingCode(FieldMap fieldMap, VariableRef source, VariableRef destination, SourceCodeContext code) {
        
        StringBuilder out = new StringBuilder();
        
        MultiOccurrenceVariableRef s = MultiOccurrenceVariableRef.from(source);
        MultiOccurrenceVariableRef d = MultiOccurrenceVariableRef.from(destination);
        
        final Class<?> destinationElementClass = d.elementType().getRawType();
        
        if (destinationElementClass == null) {
        	final String dc = destination.getOwner() == null ? "null" : destination.getOwner().rawType().getName();
            throw new MappingException("cannot determine runtime type of destination collection " + dc + "." + d.name());
        }
        
        // Start check if source property ! = null
        out.append(s.ifNotNull() + " {\n");
        
        /*
         *  TODO: migrate this to create a new destination variable first; 
         *  fill it, and then assign it to the destination using the setter. 
         */
       
        MultiOccurrenceVariableRef newDest = new MultiOccurrenceVariableRef(d.type(), "new_" + d.name());

        Type<?> destType;
        if (newDest.isArray()) {
            destType = newDest.type().getComponentType();
        } else {
            destType = newDest.type().getNestedType(0);
        }
        String idProperty = getIdProperty(destType.getRawType());

        out.append(statement(newDest.declare(""+d)));
        if (d.isAssignable()) {
            out.append(statement(newDest.ifNull() + " {"));
            out.append(statement(newDest.assign(d.newInstance(source.size()))));
            // XXX ideally this should be done for arrays, too
            if (s.isArray() || idProperty == null) {
                out.append("\n} else {\n");
                out.append("try {");
                out.append(statement("%s.clear()", newDest));
                // If filling is impossible (e.g. immutable list)
                out.append("\n} catch(UnsupportedOperationException exn) {");
                out.append(statement(newDest.assign(d.newInstance(source.size()))));
                out.append("\n}");
            }
            out.append("\n}");
        } else {
            out.append(statement("%s.clear()", newDest));
        }
        
        if (s.isArray()) {
            if (code.isDebugEnabled()) {
                code.debugField(fieldMap, "mapping " + s.elementTypeName() + "[] to Collection<" + d.elementTypeName() + ">");
            }
            
            if (s.elementType().isPrimitive()) {
                out.append("\n");
                out.append(statement("%s.addAll(asList(%s));", newDest, s));
            } else {
                out.append("\n");
                out.append(statement("%s.addAll(mapperFacade.mapAsList(asList(%s), %s.class, mappingContext));", newDest, s, d.elementTypeName()));
            }
        } else {
            if (code.isDebugEnabled()) {
                code.debugField(fieldMap, "mapping Collection<" + s.elementTypeName() + "> to Collection<" + d.elementTypeName() + ">");
            }

            // XXX ideally this should be done for arrays, too
            if (idProperty != null) {
                out.append(statement(format("%s tmp_%s = mapperFacade.mapAs%s(%s, %s, %s, mappingContext)",
                        newDest.typeName(),
                        newDest.name(),
                        d.collectionType(), s, code.usedType(s.elementType()), code.usedType(d.elementType()))));

                // keep previous elements by their ID
                append(out,
                        format("java.util.Map tmp_existing = new java.util.HashMap()"),
                        // Failes due to javassist lacking support for this
                        // format("for (Object tmp_o : %s) {", newDest.name()),
                        format("for (java.util.Iterator iterator = %s.iterator(); iterator.hasNext(); ) {", newDest.name()),
                        format("    Object tmp_o = iterator.next()"),
                        format("    Object tmp_id = ((%s) tmp_o).%s", destType.getName(), idProperty),
                        format("    tmp_existing.put(tmp_id, tmp_o)"),
                        format("}\n"));

                // clear the destination
                out.append("try {");
                out.append(statement("%s.clear()", newDest));
                // Add the next elements reusing the previous ones
                append(out,
                        // Failes due to javassist lacking support for this
                        // format("for (Object tmp_o : %s) {", newDest.name()),
                        format("for (java.util.Iterator iterator = tmp_%s.iterator(); iterator.hasNext(); ) {", newDest.name()),
                        format("    Object tmp_new_o = iterator.next()"),
                        format("    Object tmp_id = ((%s) tmp_new_o).%s", newDest.elementTypeName(), idProperty),
                        format("    if (tmp_id != null) {"),
                        format("        %s tmp_old = (%s) tmp_existing.get(tmp_id)", d.elementTypeName(), d.elementTypeName()),
                        format("        if (tmp_old != null) {"),
                        format("            mapperFacade.map(tmp_new_o, tmp_old, mappingContext)"),
                        format("            tmp_new_o = tmp_old"),
                        format("        }"),
                        format("    }"),
                        format("    %s.add(tmp_new_o)", newDest),
                        format("}"));
                // If filling is impossible (e.g. immutable list)
                out.append("\n} catch(UnsupportedOperationException exn) {");
                out.append(statement(newDest.assign(d.newInstance(source.size()))));
                append(out,
                        "\n",
                        format("%s.addAll(tmp_%s)", newDest, newDest.name()));
                out.append("\n}");

            } else {
                append(out,
                        "\n",
                        format("%s.addAll(mapperFacade.mapAs%s(%s, %s, %s, mappingContext))", newDest, d.collectionType(), s,
                                code.usedType(s.elementType()), code.usedType(d.elementType())));
            }
        }
        if (fieldMap.getInverse() != null) {
            final MultiOccurrenceVariableRef inverse = new MultiOccurrenceVariableRef(fieldMap.getInverse(), "orikaCollectionItem");
            
            if (fieldMap.getInverse().isCollection()) {
                append(out,
                          format("for (java.util.Iterator orikaIterator = %s.iterator(); orikaIterator.hasNext();) { ", newDest),
                          format("    %s orikaCollectionItem = (%s) orikaIterator.next();", d.elementTypeName(), d.elementTypeName()),
                          format("    %s { %s; }", inverse.ifNull(), inverse.assignIfPossible(inverse.newCollection())),
                          format("    %s.add(%s)", inverse, d.owner()),
                          "}");
                
            } else if (fieldMap.getInverse().isArray()) {
                out.append(" // TODO support array");
            } else {
                append(out,
                        format("for (java.util.Iterator orikaIterator = %s.iterator(); orikaIterator.hasNext();) { ", newDest),
                        format("    %s orikaCollectionItem = (%s) orikaIterator.next();", d.elementTypeName(), d.elementTypeName()),
                        inverse.assign(d.owner()),
                        "}");
                
            }
        }
        // End check if source property ! = null
        if (d.isAssignable()) {
            out.append(statement(d.assign(newDest)));
        }
        
        String assignNull = String.format("%s {\n%s;\n}", d.ifNotNull(), d.assignIfPossible("null"));
        String mapNull = shouldMapNulls(fieldMap, code) ? format(" else {\n %s;\n}", assignNull): "";
        
        append(out, "}" + mapNull);
        
        return out.toString();
    }
    
}
