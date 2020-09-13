package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testGetLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedUserOnlyViewLoginAndSignUpPages() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		driver.get(baseURL + "/result");
		Assertions.assertNotEquals("Result", driver.getTitle());
	}


	public void signup(String username,String password){

		driver.get(baseURL + "/signup");
		Signup signup = new Signup(driver);
		signup.signup("sample", "1234", username, password);
	}
	public void login(String username,String password){
		driver.get(baseURL + "/login");
		Login login = new Login(driver);
		login.login(username, password);

	}

	@Test
	public void testUserSignupLoginAndLogoutAndHomePageNoLongerAccessible() throws InterruptedException {

		String username = "sample";
		String password = "1234";

		signup(username,password);
		login(username,password);

		Assertions.assertEquals("Home", driver.getTitle());

		Home home = new Home(driver);
		home.logout();
		Assertions.assertNotEquals("Home", driver.getTitle());

	}

	@Test
	public void testNewNoteCreatedAndDisplayed(){
		String username = "sample";
		String password = "1234";

		String newNoteTitle = "New Note Title";
		String newNoteDescription = "New Note Description";

		signup(username,password);
		login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());

		Home homePage = new Home(driver);
		homePage.chooseNoteTab();
		homePage.openNewNoteModal();

		homePage.submitNote(newNoteTitle,newNoteDescription);
		Assertions.assertEquals(baseURL + "/result?success", driver.getCurrentUrl());

		Result resultPage = new Result(driver);
		resultPage.successReturnHome();

		Assertions.assertEquals("Home", driver.getTitle());
		homePage.chooseNoteTab();
		Assertions.assertEquals(newNoteTitle, homePage.getNotTitle()) ;
		homePage.openDeleteNoteModal();
		homePage.submitDeleteNote();

	}

	@Test
	public void testCurrentNoteEditedAndEditedVersionDisplayed(){
		String username = "sample";
		String password = "1234";

		String newNoteTitle = "New Note Title";
		String newNoteDescription = "New Note Description";
		String editedNoteTitle ="Edited Note Title";
		String editedNoteDescription ="Edited Note Description";

		signup(username,password);
		login(username,password);
		Home home = new Home(driver);
		home.chooseNoteTab();
		home.openNewNoteModal();

		home.submitNote(newNoteTitle,newNoteDescription);

		Result result = new Result(driver);
		result.successReturnHome();
		home.chooseNoteTab();
		home.openEditNoteModal();
		home.submitNote(editedNoteTitle,editedNoteDescription);
		result.successReturnHome();
		home.chooseNoteTab();
		Assertions.assertEquals(editedNoteTitle, home.getNotTitle()) ;
		home.openDeleteNoteModal();
		home.submitDeleteNote();

	}

	@Test
	public void testNoteDeletedAndNoLongerDisplayed(){
		String username = "sample";
		String password = "1234";

		String newNoteTitle = "New Note Title";
		String newNoteDescription = "New Note Description";

		signup(username,password);
		login(username,password);
		Home home = new Home(driver);
		home.chooseNoteTab();
		home.openNewNoteModal();

		home.submitNote(newNoteTitle,newNoteDescription);

		Result result = new Result(driver);
		result.successReturnHome();
		home.chooseNoteTab();
		home.openDeleteNoteModal();
		home.submitDeleteNote();
		Assertions.assertEquals(baseURL + "/result?success", driver.getCurrentUrl());
		result.successReturnHome();
		Assertions.assertEquals("Home", driver.getTitle());
		home.chooseNoteTab();
		Assertions.assertTrue(home.getNotes().isEmpty());
	}


}
