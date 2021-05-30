package dev.experto.demo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvokeController {

	@Autowired
	Job job2;
	@Autowired
	Job demoJob;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@RequestMapping("/help")
	public String helloWorld() {
		return "Hola mundo!!!!!!";
	}

	@RequestMapping("/invokejob/{term}")
	public String invokejob(@PathVariable String term) throws Exception{

		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addString("prueba", term).toJobParameters();
		jobLauncher.run(job2, jobParameters);
		return "Batch job invoked";
	}
	
	
    @RequestMapping("/demoJob")
    public String handledemoJob() throws Exception {
 
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(demoJob, jobParameters);
 
        return "Batch job has been invoked";
    }

}
