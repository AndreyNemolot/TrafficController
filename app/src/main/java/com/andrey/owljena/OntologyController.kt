package com.andrey.owljena

import com.hp.hpl.jena.ontology.DatatypeProperty
import com.hp.hpl.jena.ontology.Individual
import com.hp.hpl.jena.ontology.ObjectProperty
import com.hp.hpl.jena.ontology.OntClass
import com.hp.hpl.jena.query.QueryExecutionFactory
import com.hp.hpl.jena.query.QueryFactory
import com.hp.hpl.jena.query.ResultSetFormatter
import com.hp.hpl.jena.query.ResultSetFactory
import com.hp.hpl.jena.rdf.model.RDFNode
import com.hp.hpl.jena.query.QuerySolution




class OntologyController {

    private val ontologyProvider: OntologyProvider
    private val owlNamespace: String
    private var isOntologyLoaded = false

    init {
        ontologyProvider = OntologyProvider.instance
        isOntologyLoaded = ontologyProvider.ontology != null
        owlNamespace = ontologyProvider.ontology.getNsPrefixURI("")
    }

    internal fun getClass(uri: String): OntClass {
        return ontologyProvider.ontology.getOntClass(owlNamespace + uri)
    }

    internal fun getSuperClass(name: String): OntClass {
        return getClass(name).getSuperClass()
    }

    internal fun getSuperClass(ontClass: OntClass): OntClass {
        return ontClass.superClass
    }

    internal fun createDatatypeProperty(name: String) {
        ontologyProvider.ontology.createDatatypeProperty(owlNamespace + name)
    }

    internal fun createObjectProperty(name: String) {
        ontologyProvider.ontology.createObjectProperty(owlNamespace + name)
    }

    internal fun getDatatypeProperty(name: String): DatatypeProperty {
        return ontologyProvider.ontology.getDatatypeProperty(owlNamespace + name)
    }

    internal fun getObjectProperty(name: String): ObjectProperty {
        return ontologyProvider.ontology.getObjectProperty(owlNamespace + name)
    }

    internal fun createIndividual(res: OntClass, name: String): Individual {
        return ontologyProvider.ontology.createIndividual(name, res)
    }

//    internal fun listClasses(): List<String> {
//        val list = ArrayList<String>()
//        ontologyProvider.ontology.listClasses().forEach { it ->
//            list.add(it.localName)
//        }
//        return list
//    }
//
//    internal fun listDatatypeProperties(): List<String> {
//        val list = ArrayList<String>()
//        ontologyProvider.ontology.listDatatypeProperties().forEach { it ->
//            list.add(it.localName)
//        }
//        return list
//    }
//
//    internal fun listObjectProperties(): List<String> {
//        val list = ArrayList<String>()
//        ontologyProvider.ontology.listObjectProperties().forEach { it ->
//            list.add(it.localName)
//        }
//        return list
//    }
//
//    internal fun listIndividuals(): List<String> {
//        val list = ArrayList<String>()
//        ontologyProvider.ontology.listIndividuals().forEach { it ->
//            list.add(it.localName)
//        }
//        return list
//    }

    internal fun query(queryString: String) {

        val query = QueryFactory.create(queryString);
        val qexec = QueryExecutionFactory.create(query, ontologyProvider.ontology)
        val results = qexec.execSelect()
        //ResultSetFormatter.out(System.out, results, query)

        while (results.hasNext()) {
            val soln = results.nextSolution()
            val r = soln.getResource("?x").localName // Get a result variable - must be a resource
            ResultSetFormatter.out(System.out, results, query)
        }

        qexec.close()




    }
}