package com.andrey.owljena

import com.clarkparsia.owlapiv3.OWL.factory
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory


import org.semanticweb.owlapi.apibinding.OWLManager
import java.io.File
import java.io.FileOutputStream
import org.semanticweb.owlapi.reasoner.SimpleConfiguration

import org.semanticweb.owlapi.reasoner.OWLReasoner
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer
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










class OntologyController {

    private var ontologyProvider: OntologyProvider
    private var isOntologyLoaded = false
    private val ontIRI = "http://www.owl-ontologies.com/roadRules"

    init {
        ontologyProvider = OntologyProvider.instance
        isOntologyLoaded = ontologyProvider.ontology != null
        //owlNamespace = ontologyProvider.ontology.getNsPrefixURI("")
    }
//
//    internal fun getClass(uri: String): OntClass {
//        return ontologyProvider.ontology.getOntClass(owlNamespace + uri)
//    }
//
//    internal fun getSuperClass(name: String): OntClass {
//        return getClass(name).getSuperClass()
//    }
//
//    internal fun getSuperClass(ontClass: OntClass): OntClass {
//        return ontClass.superClass
//    }
//
//    internal fun createDatatypeProperty(name: String) {
//        ontologyProvider.ontology.createDatatypeProperty(owlNamespace + name)
//    }
//
//    internal fun createObjectProperty(name: String) {
//        ontologyProvider.ontology.createObjectProperty(owlNamespace + name)
//    }
//
//    internal fun getDatatypeProperty(name: String): DatatypeProperty {
//        return ontologyProvider.ontology.getDatatypeProperty(owlNamespace + name)
//    }
//
//    internal fun getObjectProperty(name: String): ObjectProperty {
//        return ontologyProvider.ontology.getObjectProperty(owlNamespace + name)
//    }
//
    internal fun createIndividual(file: File) {
    val manager = OWLManager.createOWLOntologyManager()
    val ontologyIRI = IRI.create(ontIRI)
    val cls = manager.owlDataFactory.getOWLClass(IRI.create(ontIRI + "Passenger"))
    val ind = factory.getOWLNamedIndividual(IRI.create(ontIRI + "JAVA"))
    val axiom = factory.getOWLClassAssertionAxiom(cls, ind)
    val addAxion = AddAxiom(ontologyProvider.ontology, axiom)
    manager.applyChange(addAxion)
    //val out = FileOutputStream(file)
    //manager.saveOntology(ontologyProvider.ontology,out)

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
        val manager = OWLManager.createOWLOntologyManager()
        val reasoner =
            PelletReasonerFactory.getInstance().createReasoner(ontologyProvider.ontology, SimpleConfiguration())
        reasoner.kb.realize()


        val factory = manager.owlDataFactory

        //val pm = manager.getOntologyFormat(ontologyProvider.ontology).asPrefixOWLOntologyFormat()


        val p = PrefixOWLOntologyFormat()
        //p.setDefaultPrefix(ontologyProvider.ontology.getOntologyID().getOntologyIRI().toString() + "#");

        p.defaultPrefix="http://www.owl-ontologies.com/roadRules.owl#"


        val nissan = factory.getOWLNamedIndividual(":Nissan", p)


        nissan.getDataPropertyValues(ontologyProvider.ontology) // сперва сохранять онтологию

        reasoner.getDataPropertyValues(nissan, ontologyProvider.ontology.getDataPropertiesInSignature().elementAt(2)) //получать проперти из ризонера

        //val prop = OWLDataProperty()

        val asd = reasoner.kb.individuals
        val out = FileOutputStream(file)
        val qwe = asd.elementAt(13)


//        val exportedOntology = manager.createOntology(IRI.create(file.toURI()))
//
//        val generator = InferredOntologyGenerator(reasoner)
//        generator.fillOntology(manager, ontologyProvider.ontology)
//
//        manager.saveOntology(ontologyProvider.ontology, RDFXMLOntologyFormat(), IRI.create(file.toURI()))


//        val axiomGenerators = ArrayList<InferredAxiomGenerator<out OWLAxiom>>()
//        axiomGenerators.add(InferredPropertyAssertionGenerator())
//
//        val iog = InferredOntologyGenerator(reasoner, axiomGenerators)
//        iog.fillOntology(manager, ontologyProvider.ontology)
//
//        val owlOutputStream = ByteArrayOutputStream()
//        manager.saveOntology(ontologyProvider.ontology, owlOutputStream)
//        System.out.println("NEW!")
//        System.out.println(owlOutputStream.toString())
    }

    private fun listSWRLRules(ontology: OWLOntology) {
        val renderer = DLSyntaxObjectRenderer()
        for (rule in ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
            System.out.println(renderer.render(rule))
        }
    }

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