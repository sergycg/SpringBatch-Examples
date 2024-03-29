package dev.experto.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.experto.demo.domain.CreditCard;
import dev.experto.demo.domain.CreditCardRisk;
import dev.experto.demo.job.CreditCardItemProcessor;
import dev.experto.demo.job.CreditCardItemReader;
import dev.experto.demo.job.CreditCardItemWriter;
import dev.experto.demo.listener.CreditCardIItemReaderListener;
import dev.experto.demo.listener.CreditCardIItemWriterListener;
import dev.experto.demo.listener.CreditCardItemProcessListener;
import dev.experto.demo.listener.CreditCardJobExecutionListener;
import dev.experto.demo.tasks.MyTaskOne;
import dev.experto.demo.tasks.MyTaskTwo;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public CreditCardItemReader reader() {
        return new CreditCardItemReader();
    }

    @Bean
    public CreditCardItemProcessor processor() {
        return new CreditCardItemProcessor();
    }

    @Bean
    public CreditCardItemWriter writer() {
        return new CreditCardItemWriter();
    }

    @Bean
    public CreditCardJobExecutionListener jobExecutionListener() {
        return new CreditCardJobExecutionListener();
    }

    @Bean
    public CreditCardIItemReaderListener readerListener() {
        return new CreditCardIItemReaderListener();
    }

    @Bean
    public CreditCardItemProcessListener creditCardItemProcessListener() {
        return new CreditCardItemProcessListener();
    }

    @Bean
    public CreditCardIItemWriterListener writerListener() {
        return new CreditCardIItemWriterListener();
    }

    @Bean
    public Job job(Step step, CreditCardJobExecutionListener jobExecutionListener) {
        Job job = jobBuilderFactory.get("job1")
                .listener(jobExecutionListener)
                .flow(step)
                .end()
                .build();
        return job;
    }

    @Bean
    public Job job2(Step step2, CreditCardJobExecutionListener jobExecutionListener) {
        Job job = jobBuilderFactory.get("job2")
                .listener(jobExecutionListener)
                .flow(step2)
                .end()
                .build();
        return job;
    }

    @Bean
    public Step step(CreditCardItemReader reader,
                     CreditCardItemWriter writer,
                     CreditCardItemProcessor processor,
                     CreditCardIItemReaderListener readerListener,
                     CreditCardItemProcessListener creditCardItemProcessListener,
                     CreditCardIItemWriterListener writerListener) {

        TaskletStep step = stepBuilderFactory.get("step1")
                .<CreditCard, CreditCardRisk>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(creditCardItemProcessListener)
                .listener(writerListener)
                .build();
        return step;
    }

    @Bean
    public Step step2(CreditCardItemReader reader,
                     CreditCardItemWriter writer,
                     CreditCardItemProcessor processor,
                     CreditCardIItemReaderListener readerListener,
                     CreditCardItemProcessListener creditCardItemProcessListener,
                     CreditCardIItemWriterListener writerListener) {

        TaskletStep step = stepBuilderFactory.get("step2")
                .<CreditCard, CreditCardRisk>chunk(4)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(creditCardItemProcessListener)
                .listener(writerListener)
                .build();
        return step;
    }
    
    
    
    
    
/****************** DEMOJOB con 2 STEPS ****************/
    @Bean
    public Step stepOne(){
        return stepBuilderFactory.get("stepOne")
                .tasklet(new MyTaskOne())
                .build();
    }
     
    @Bean
    public Step stepTwo(){
        return stepBuilderFactory.get("stepTwo")
                .tasklet(new MyTaskTwo())
                .build();
    }   
     
    @Bean
    public Job demoJob(){
        return jobBuilderFactory.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

    
    

}
