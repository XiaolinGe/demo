package com.lynn.demoanz;

import com.lynn.demoanz.entity.Customer;
import com.lynn.demoanz.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldReturnAllTxs() throws Exception {
        //given
        createTestedTxs();

        //when
        var result = mockMvc.perform(get("/transactions"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(2)))

                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].amount").value("111.11"))
                .andExpect(jsonPath("$.[0].txNumber").value("10001"))
                .andExpect(jsonPath("$.[0].enabled").value(true))

                .andExpect(jsonPath("$.[1].id").exists())
                .andExpect(jsonPath("$.[1].amount").value("222.22"))
                .andExpect(jsonPath("$.[1].txNumber").value("10002"))
                .andExpect(jsonPath("$.[1].enabled").value(true));
    }

    @Test
    public void createTxSuccessfully() throws Exception {
        //when
        var body = "{" +
                "\"amount\":\"1.1\"," +
                "\"txNumber\":\"1\"," +
                "\"enabled\":\"true\"" +
                "}";

        var result = mockMvc.perform(post("/transactions").content(body).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").value("1.1"))
                .andExpect(jsonPath("$.txNumber").value("1"));

    }

    @Test
    public void createTxFailedWithInvalidJson() throws Exception {
        //when
        var body = "{" +
                "\"amount\":\"1.1\"" +
                "\"txNumber\":\"1\"" +
                "}";

        var result = mockMvc.perform(post("/transactions").content(body).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("JSON parse error")));
    }

    @Test
    public void createTxFailedWhenAmountIsMissing() throws Exception {
        //when
        var body = "{" +
                "\"txNumber\":\"1\"," +
                "\"enabled\":\"true\"" +
                "}";

        var result = mockMvc.perform(post("/transactions").content(body).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid parameter(s)."))
                .andExpect(jsonPath("$.errorFields[*]", hasSize(1)))
                .andExpect(jsonPath("$.errorFields[0].field").value("amount"));
    }


    @Test
    public void createTxFailedWhenAmountIsNotNumber() throws Exception {
        //when
        var body = "{" +
                "\"amount\":\"aaa\"" +
                "\"txNumber\":\"1\"" +
                "}";
        var result = mockMvc.perform(post("/transactions").content(body).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("JSON parse error")));
    }

    @Test
    public void createTxFailedWhenAmountFractionalDigitsOutOfBound() throws Exception {
        //when
        var body = "{" +
                "\"amount\":\"99.1234\"," +
                "\"txNumber\":\"1\"," +
                "\"enabled\":\"true\"" +
                "}";
        var result = mockMvc.perform(post("/transactions").content(body).contentType(MediaType.APPLICATION_JSON_VALUE));


        //then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid parameter(s)."))
                .andExpect(jsonPath("$.errorFields[*]", hasSize(1)))
                .andExpect(jsonPath("$.errorFields[0].field").value("amount"));
    }

    private List<Transaction> createTestedTxs() {
        var customer = new Customer();
        customer.setUsername("lynn");
        customer.setEmail("lynn@test.com");
        customer.setEnabled(true);
        customer.setCustomerNumber("10001");
        customer.setAddress("Auckland, NZ");
        entityManager.persist(customer);

        var tx1 = new Transaction();
        tx1.setAmount(new BigDecimal("111.11"));
        tx1.setTxNumber("10001");
        tx1.setEnabled(true);
        tx1.setCustomer(customer);
        entityManager.persist(tx1);

        var tx2 = new Transaction();
        tx2.setAmount(new BigDecimal("222.22"));
        tx2.setTxNumber("10002");
        tx2.setEnabled(true);
        tx2.setCustomer(customer);
        entityManager.persist(tx2);

        var txs =  new ArrayList<Transaction>();
        txs.add(tx1);
        txs.add(tx2);

        return txs;
    }


}
