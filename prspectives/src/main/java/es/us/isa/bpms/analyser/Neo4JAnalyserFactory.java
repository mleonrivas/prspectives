package es.us.isa.bpms.analyser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.impl.util.StringLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.google.gson.Gson;

import es.us.isa.bpms.model.ConverterHelper;
import es.us.isa.bpms.model.Model;
import es.us.isa.bpms.repository.ModelRepository;
import es.us.isa.cristal.BPEngine;
import es.us.isa.cristal.analyser.RALAnalyser;
import es.us.isa.cristal.neo4j.analyzer.Neo4jRALAnalyser;
import es.us.isa.cristal.neo4j.analyzer.util.DesignTimeAnalyserBPEngine;
import es.us.isa.cristal.organization.model.gson.Document;
import es.us.isa.cristal.organization.model.util.IOUtil;


public class Neo4JAnalyserFactory implements AnalyserFactory{

	@Autowired
    private ModelRepository modelRepository;
	
	@Autowired
    private ConverterHelper converterHelper;
	
	@Override
	public RALAnalyser getAnalyser(String processId, String modelId, String organizationId, String assignment) throws Exception {
		String context = organizationId==null? "analyser" : "assignment";
		//System.out.println("CREATE NEW ANALYZER:" + ralExpr);
		
		Model m = getModel(context, modelId);
		//System.out.println(bpmn);
		String orgId = organizationId==null? m.getOrganization() : organizationId;
		
		String bpmn = getBpmn(m,context, modelId);
		
		ExecutionEngine engine = getExecutionEngine(orgId);

		
		Gson gson = new Gson();
		Map<String,String> assignMap = gson.fromJson(assignment, HashMap.class);
		BPEngine bpEngine = new DesignTimeAnalyserBPEngine(bpmn, assignMap);
		Neo4jRALAnalyser analyzer = new Neo4jRALAnalyser(engine, bpEngine, processId);

		
		
		return analyzer;
	}
    
	@Cacheable(value = "modelCache", key = "#organizationId")
    private ExecutionEngine getExecutionEngine(String organizationId){
		
		
    	InputStream is = modelRepository.getModelReader(organizationId);
		
		String organization = IOUtil.convertStreamToString(is);
		//System.out.println(organization);
		Document doc = Document.importFromJson(organization);
		ExecutionEngine engine;
		
		String dirName = System.getenv("TEMP") + File.separator + "neo4j-" + UUID.randomUUID().toString();
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
		.newEmbeddedDatabaseBuilder(dirName)
		.setConfig(GraphDatabaseSettings.node_keys_indexable,"name, position, role, unit")
		.setConfig(GraphDatabaseSettings.node_auto_indexing, "true")
		.newGraphDatabase();
		engine = new ExecutionEngine( graphDb,StringLogger.logger(new File(dirName + File.separator + "log.log")) );
		engine.execute(doc.getCypherCreateQuery());
		
		return engine;
    }
 
	@Cacheable(value = "modelCache", key = "#modelId.concat('/').concat(#context)")
    private String getBpmn(Model m, String context, String modelId){
		return converterHelper.createAndStoreXml(m);
    }
	
	@Cacheable(value = "modelCache", key = "#modelId.concat('/').concat(#context)")
    private Model getModel(String context, String modelId){
    	
    	return modelRepository.getModel(modelId);
    }

}
