package dev.experto.demo.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTaskOne implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception 
	{
        System.out.println("MyTaskOne start..");
 
        // ... your code
         
        System.out.println("MyTaskOne done..");
        return RepeatStatus.FINISHED;
    }    

}
