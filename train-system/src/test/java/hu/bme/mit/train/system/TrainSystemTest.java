package hu.bme.mit.train.system;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import hu.bme.mit.train.system.TrainTachograf;



public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	TrainSystem system;
	TrainTachograf tachograf;
	
	@Before
	public void before() {
		system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tachograf = new TrainTachograf(system);

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void Emergancybraketest() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		Assert.assertEquals(4, controller.getReferenceSpeed());
		controller.emergancyset(true);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void tachotest() {
		tachograf.addrow();
		Assert.assertEquals(tachograf.getvalue(LocalDate.now(), system.getUser().getJoystickPosition()), controller.getReferenceSpeed());
	}
	
}
