package dev.experto.demo.job;

import dev.experto.demo.domain.CreditCard;
import dev.experto.demo.repository.CreditCardRepository;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class CreditCardItemReader implements ItemReader<CreditCard> {

    @Autowired
    private CreditCardRepository respository;

    private Iterator<CreditCard> usersIterator;

    @BeforeStep
    public void before(StepExecution stepExecution) {
    	JobParameters params = stepExecution.getJobExecution().getJobParameters();
    	Long time = params.getLong("time");
    	
        usersIterator = respository.findAll().iterator();
        String term = params.getString("prueba");
    }

    @Override
    public CreditCard read() {
        if (usersIterator != null && usersIterator.hasNext()) {
            return usersIterator.next();
        } else {
            return null;
        }
    }
}
