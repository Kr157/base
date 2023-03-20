package hu.bme.mit.train.system;
import java.sql.Date;
import java.time.LocalDate;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hu.bme.mit.train.system.TrainSystem;

class TrainTachograf{
    private TrainSystem system;

    Table<LocalDate, Integer, Integer> tachograf = HashBasedTable.create();

    public TrainTachograf(TrainSystem system){
        this.system = system;
    }

    public void addrow() {
		tachograf.put(LocalDate.now(), system.getUser().getJoystickPosition(), system.getController().getReferenceSpeed());
	}

    public int getvalue(LocalDate date, Integer joystickPosition){
        return tachograf.get(date, joystickPosition);
    }
}