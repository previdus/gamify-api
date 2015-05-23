import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import com.core.api.beans.ApiResult;
import com.core.api.controller.ApiContentController;
import com.core.domain.lms.Exam;
import com.core.service.ExamService;


@RunWith(PowerMockRunner.class)
public class DataEntryControllerTest {
	
	//SUT
	@InjectMocks
	private ApiContentController apiDataEntryController;
	
	//Collaborator
	@Mock
	private ExamService apiDataEntryService;
	
	//@Mock
	//private ReloadableResourceBundleMessageSource messageSource;
	
	
	private final String bankExamName = "BANK-PO";
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);		
		//messageSource.setBasename("/WEB-INF/messages/messages");
		
	}
	
	
	
	

	@Test
	public void testAddExam(){
		
		Exam mockedExam = Mockito.mock(Exam.class);
		mockedExam.setExamName(bankExamName);
		mockedExam.setId(1L);
		Mockito.when(apiDataEntryService.addExam(bankExamName)).thenReturn(mockedExam);
		//Mockito.when(messageSource.getMessage(Mockito.anyString(),Mockito.any(Object[].class), Mockito.any(Locale.class))).thenReturn("You have successfully added this exam");		
		ApiResult apiResult = apiDataEntryController.addExam(bankExamName);
		Mockito.verify(apiDataEntryService).addExam(bankExamName);
		assertNotNull(apiResult);
		assertThat(apiResult.getStatus(),is(1));		
	}

}
