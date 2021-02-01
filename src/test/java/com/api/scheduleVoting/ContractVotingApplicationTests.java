package com.api.scheduleVoting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ContractVotingApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void initApplication(){
		ScheduleVotingApplication.main(new String[]{});
	}

}
