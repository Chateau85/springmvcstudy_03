package hello.itemservice;

import hello.itemservice.domain.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemRepository itemRepository;

	@BeforeEach
	void setUp() {
		itemRepository.clearStore();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void itemCrudPagesAreRendered() throws Exception {
		mockMvc.perform(post("/basic/items/add")
						.param("itemName", "item")
						.param("price", "10000")
						.param("quantity", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/basic/items/1?status=true"));

		mockMvc.perform(get("/basic/items/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("item")));

		mockMvc.perform(post("/basic/items/1/edit")
						.param("itemName", "updated")
						.param("price", "20000")
						.param("quantity", "20"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/basic/items/1"));
	}

	@Test
	void thymeleafEscapesItemName() throws Exception {
		mockMvc.perform(post("/basic/items/add")
						.param("itemName", "<script>alert(1)</script>")
						.param("price", "10000")
						.param("quantity", "10"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(get("/basic/items/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("&lt;script&gt;alert(1)&lt;/script&gt;")));
	}

	@Test
	void invalidItemIsRejected() throws Exception {
		mockMvc.perform(post("/basic/items/add")
						.param("itemName", " ")
						.param("price", "-1")
						.param("quantity", "10"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void missingItemReturnsNotFound() throws Exception {
		mockMvc.perform(get("/basic/items/999"))
				.andExpect(status().isNotFound());
	}

}
