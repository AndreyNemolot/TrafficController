package com.andrey.owljena


import com.hp.hpl.jena.util.FileManager
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology

import java.io.IOException
import java.io.InputStream
import org.semanticweb.owlapi.model.OWLOntologyManager
import java.io.File


class OntologyProvider private constructor() {
    lateinit var ontology: OWLOntology

//    fun loadOntology(ontologyPath: String) {
//        val ontologyIn = FileManager.get().open(ontologyPath)
//        loadOntology(ontologyIn)
//
//    }

    fun loadOntology(inpupStream: InputStream) {
        val manager = OWLManager.createOWLOntologyManager()
        ontology = manager.loadOntologyFromOntologyDocument(inpupStream)
        //ontology.write(System.out)
//        try {
//            inpupStream.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }

    }

    companion object {
        val instance = OntologyProvider()
    }
}
