package com.andrey.owljena

import com.clarkparsia.owlapiv3.OWL.factory
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory


import org.semanticweb.owlapi.apibinding.OWLManager
import java.io.File
import java.io.FileOutputStream
import org.semanticweb.owlapi.reasoner.SimpleConfiguration

import org.semanticweb.owlapi.reasoner.OWLReasoner
//import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer
import com.hp.hpl.jena.assembler.JA.reasoner
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import org.semanticweb.owlapi.model.OWLIndividual
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import com.clarkparsia.owlapiv3.OWL.manager
import org.semanticweb.owlapi.model.AddAxiom
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import com.clarkparsia.owlapiv3.OWL.manager
import com.clarkparsia.pellet.owlapiv3.PelletReasoner
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat
import org.semanticweb.owlapi.util.InferredAxiomGenerator
import org.semanticweb.owlapi.util.InferredOntologyGenerator
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator
import java.io.ByteArrayOutputStream
import com.clarkparsia.owlapiv3.OWL.manager
import org.semanticweb.owlapi.model.OWLDataProperty
import com.hp.hpl.jena.assembler.JA.reasoner
import org.semanticweb.owlapi.model.OWLLiteral
import org.semanticweb.owlapi.vocab.OWL2Datatype
import org.semanticweb.owlapi.vocab.OWLFacet


class OntologyController {

    private var ontologyProvider: OntologyProvider
    private var isOntologyLoaded = false
    private val ontIRI = "http://www.owl-ontologies.com/roadRules.owl"
    private lateinit var pm: PrefixOWLOntologyFormat

    init {
        ontologyProvider = OntologyProvider.instance
        isOntologyLoaded = ontologyProvider.ontology != null
        pm = manager.getOntologyFormat(ontologyProvider.ontology).asPrefixOWLOntologyFormat()
        //owlNamespace = ontologyProvider.ontology.getNsPrefixURI("")
    }


    internal fun createIndividual(file: File, indClass: String, indName: String) {


        val cls = manager.owlDataFactory.getOWLClass(IRI.create(ontIRI + "#" + indClass))

        val ind = factory.getOWLNamedIndividual(":" + indName, pm)
        val axiom = factory.getOWLClassAssertionAxiom(cls, ind)
        val addAxion = AddAxiom(ontologyProvider.ontology, axiom)
        manager.applyChange(addAxion)
        //val out = FileOutputStream(file)
        //manager.saveOntology(ontologyProvider.ontology, out)

    }

    internal fun createDatatypeProperty(indClass: String, indName: String, propertyName: String, value: String){
        val cls = manager.owlDataFactory.getOWLClass(IRI.create(ontIRI + "#" + indClass))
        val ind = factory.getOWLNamedIndividual(":" + indName, pm)

        val hasTodo = factory.getOWLDataProperty(propertyName, pm)
        val dataPropertyAssertion = factory
            .getOWLDataPropertyAssertionAxiom(hasTodo, ind, value);


        manager.addAxiom(ontologyProvider.ontology, dataPropertyAssertion);
        val file = File("/data/user/0/com.andrey.owljena/files", "newOnt.owl")
        val out = FileOutputStream(file)
        manager.saveOntology(ontologyProvider.ontology, out)
    }


    internal fun query(queryString: String) {

//        val query = QueryFactory.create(queryString);
//        val qexec = QueryExecutionFactory.create(query, ontologyProvider.ontology)
//        val results = qexec.execSelect()
//        //ResultSetFormatter.out(System.out, results, query)
//
//        while (results.hasNext()) {
//            val soln = results.nextSolution()
//            val r = soln.getResource("?x").localName // Get a result variable - must be a resource
//            ResultSetFormatter.out(System.out, results, query)
//        }
//
//        qexec.close()

    }

    internal fun swrl(file: File) {
        //val manager = OWLManager.createOWLOntologyManager()
        val reasoner =
            PelletReasonerFactory.getInstance().createReasoner(ontologyProvider.ontology, SimpleConfiguration())
        reasoner.kb.realize()


        val factory = manager.owlDataFactory


       // val pm = manager.getOntologyFormat(ontologyProvider.ontology).asPrefixOWLOntologyFormat()

        val nissan = factory.getOWLNamedIndividual(":Nissan", pm)

        //get values of selected properties on the individual
        val todo = factory.getOWLDataProperty(":toDo", pm)

        for (email in reasoner.getDataPropertyValues(nissan, todo)) {
            println("TODO: " + email.literal)
        }


        saveOntology(file, reasoner)

    }

    fun saveOntology(file: File, reasoner: PelletReasoner) {

        val out = FileOutputStream(file)

        val axiomGenerators = ArrayList<InferredAxiomGenerator<out OWLAxiom>>()
        axiomGenerators.add(InferredPropertyAssertionGenerator())

        val iog = InferredOntologyGenerator(reasoner, axiomGenerators)
        iog.fillOntology(manager, ontologyProvider.ontology)

        manager.saveOntology(ontologyProvider.ontology, out)
    }

//    private fun listSWRLRules(ontology: OWLOntology) {
//        val renderer = DLSyntaxObjectRenderer()
//        for (rule in ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
//            System.out.println(renderer.render(rule))
//        }
//    }

//    private fun getIndividuals(
//        superclass: String,
//        o: OWLOntology
//    ): Set<OWLIndividual> {
//
//        val reasoner = PelletReasonerFactory.getInstance()
//            .createReasoner(o)
//        val instances = reasoner.getInstances(
//            OWL.Class(IRI.create(superclass)), false
//        ).flattened
//
//        // filter out all owl:sameAs instances...
//        val ind = TreeSet()
//        for (i in instances) {
//            ind.add(i)
//        }
//        logger.info("|I| = " + ind.size)
//        logger.debug("I = $ind")
//
//        return ind
//
//    }


}