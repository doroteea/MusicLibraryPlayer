package com.lab4.demo.batch;

import javax.sql.DataSource;

import com.lab4.demo.track.model.TrackBatch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.TimerTask;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
@Bean
public FlatFileItemReader<TrackBatch> reader() {
    return new FlatFileItemReaderBuilder<TrackBatch>()
            .name("TrackBatchItemReader")
            .resource(new ClassPathResource("Book1.csv"))
            .delimited()
            .names("id", "artist", "album", "duration", "explicit_lyrics", "link","preview", "title")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<TrackBatch>() {{
                setTargetType(TrackBatch.class);
            }})
            .build();
}

    @Bean
    public TrackBatchItemProcessor processor() {
        return new TrackBatchItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TrackBatch> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TrackBatch>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO tracks (id, artist, album,duration,explicit_lyrics,link,preview,title) VALUES (:id, :artist, :album, :duration, :explicit_lyrics, :link, :preview, :title)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<TrackBatch> writer) {
        return stepBuilderFactory.get("step1")
                .<TrackBatch, TrackBatch> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }



}