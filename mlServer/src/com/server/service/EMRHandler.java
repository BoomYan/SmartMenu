package com.server.service;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsResult;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeClusterResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;

public class EMRHandler {

	public static void sendStepsIntoEMR() {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIEYCREGYLSV6HKEQ", "wd5zQOurablxowDfnIvQGUSQqYIC68mMbvw1WxWE");
		AmazonElasticMapReduce emr = new AmazonElasticMapReduceClient(credentials).withRegion(Regions.US_WEST_2);
		 
		AddJobFlowStepsRequest req = new AddJobFlowStepsRequest();
		req.withJobFlowId("j-23EM2VICJUC7Q");

		List<StepConfig> stepConfigs = new ArrayList<StepConfig>();
				
		HadoopJarStepConfig sparkStepConf = new HadoopJarStepConfig()
					.withJar("command-runner.jar")
					.withArgs("spark-submit","--executor-memory","1g","--class","ModelBuilder","/Users/sunxiangyu/IdeaProjects/test/out/artifacts/test_jar/test.jar","10");			
				
		StepConfig sparkStep = new StepConfig()
					.withName("Spark Step")
					.withActionOnFailure("CONTINUE")
					.withHadoopJarStep(sparkStepConf);

		stepConfigs.add(sparkStep);
		req.withSteps(stepConfigs);
		AddJobFlowStepsResult result = emr.addJobFlowSteps(req);
		
		//get cluster status
		DescribeClusterResult clusterResult = emr.describeCluster(new DescribeClusterRequest().withClusterId(""));
		
		
		
	}
	
}
