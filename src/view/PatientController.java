package view;

import model.Patient;

/**
 * This interface is to be implemented by Controller classes which need to
 * receive a Patient object passed in by other views / scenes
 */
public interface PatientController {

    void receivePatient(Patient patient);

}
