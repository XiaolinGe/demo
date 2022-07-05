package com.lynn.demoanz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynn.demoanz.repository.TransactionRepository;
import com.lynn.demoanz.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {
        TransactionService.class,
        TransactionRepository.class,
        ObjectMapper.class,
})
public class ServiceTests {
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testGetTxService() {
        //given

        //when

        //then
    }
}
