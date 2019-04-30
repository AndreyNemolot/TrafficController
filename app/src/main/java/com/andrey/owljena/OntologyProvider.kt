package com.andrey.owljena

import com.hp.hpl.jena.ontology.OntModel
import com.hp.hpl.jena.ontology.OntModelSpec
import com.hp.hpl.jena.rdf.model.ModelFactory
import com.hp.hpl.jena.util.FileManager

import java.io.IOException

class OntologyProvider private constructor() {
    var ontology: OntModel

    init {
        ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF)
    }

    fun loadOntology(ontologyPath: String) {
        val ontologyIn = FileManager.get().open(ontologyPath)
        ontology.read(ontologyIn, "RDF/XML")
        ontology.write(System.out)
        try {
            ontologyIn.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {
        val instance = OntologyProvider()
    }
}
