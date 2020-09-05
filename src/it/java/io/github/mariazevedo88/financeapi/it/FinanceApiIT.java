package io.github.mariazevedo88.financeapi.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.financeapi.enumeration.TransactionTypeEnum;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 10/09/2019
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class FinanceApiIT {
	
	@Autowired
    private MockMvc mockMvc;

	@Test
	@Order(1)
	public void contextLoad() {
		assertNotNull(mockMvc);
	}
	
	@Test
	@Order(2)
	public void shouldReturnCreateATransaction() throws Exception {

		JSONObject mapToCreate = setObjectToCreate();
		this.mockMvc.perform(post("/financial/v1/transactions").contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapToCreate))).andExpect(status().isCreated());
	}
	
	@Test
	@Order(3)
	public void shouldReturnUpdateATransaction() throws Exception {
		
		JSONObject mapToUpdate = setObjectToUpdate();
		this.mockMvc.perform(put("/financial/v1/transactions/1").contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapToUpdate))).andExpect(status().isOk());
	}

	@Test
	@Order(4)
    public void shouldReturnGetAllTransactions() throws Exception {
		this.mockMvc.perform(get("/financial/v1/transactions")).andExpect(status().isOk());
    }
	
	@Test
	@Order(5)
    public void shouldReturnRemoveAllTransactions() throws Exception {
		this.mockMvc.perform(delete("/financial/v1/transactions")).andExpect(status().isNoContent());
    }
	
	@SuppressWarnings("unchecked")
	private JSONObject setObjectToCreate() {
		
		String localDate = LocalDateTime.now().toString().concat("Z");
		
		JSONObject map = new JSONObject();
		map.put("id", 1L);
		map.put("nsu", "220788");
		map.put("autorizationNumber", "010203");
		map.put("amount", "22.88");
		map.put("transactionDate", localDate);
		map.put("type", TransactionTypeEnum.CARD);
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject setObjectToUpdate() {
		
		String localDate = LocalDateTime.now().toString().concat("Z");
		
		JSONObject map = new JSONObject();
		map.put("id", 1L);
		map.put("nsu", "220788");
		map.put("autorizationNumber", "000000");
		map.put("amount", "22.88");
		map.put("transactionDate", localDate);
		map.put("type", TransactionTypeEnum.CARD);
		
		return map;
	}

}
