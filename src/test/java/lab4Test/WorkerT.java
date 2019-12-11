package lab4Test;

import lab4.Worker;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class WorkerT {
    @Test(expectedExceptions = IllegalStateException.class)
    public void workerBuilderTest() {
        Worker worker = new Worker.Builder()
                .setId(-1)
                .setBirthDay(LocalDate.now())
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setSalary(1000.0)
                .build();
    }

}
