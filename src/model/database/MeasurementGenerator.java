package model.database;

import model.Measurement;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates collections of random measurements, organized by different "packs" of
 * blood tests
 */
public class MeasurementGenerator {

    private DBManager dbManager = new DBManager();

    public MeasurementGenerator() {}

    //gathers a specific collection of random measurements according to the chosen pack of blood tests
    public ArrayList<Measurement> getSingleMeasurementPack(int packNumber) {
        ArrayList<Measurement> allMeasurementsToTake = new ArrayList<>();

        if (packNumber == 1) { //blood + coag + immunology
            allMeasurementsToTake.addAll(getHematologyPanel());
            allMeasurementsToTake.addAll(getWhiteBloodCellPanel());
            allMeasurementsToTake.addAll(getCoagulationPanel());
            allMeasurementsToTake.addAll(getImmunologyPanel());
        } else if (packNumber == 2) { //blood + ions + renal
            allMeasurementsToTake.addAll(getHematologyPanel());
            allMeasurementsToTake.addAll(getWhiteBloodCellPanel());
            allMeasurementsToTake.addAll(getIonsAndTraceMetals());
            allMeasurementsToTake.addAll(getRenalFunctionPanel());
        } else if (packNumber == 3) { //blood + coag + liver + lipids
            allMeasurementsToTake.addAll(getHematologyPanel());
            allMeasurementsToTake.addAll(getWhiteBloodCellPanel());
            allMeasurementsToTake.addAll(getCoagulationPanel());
            allMeasurementsToTake.addAll(getLiverFunctionPanel());
            allMeasurementsToTake.addAll(getLipidProfile());
        } else if (packNumber == 4) { //hormones
            allMeasurementsToTake.addAll(getHormonesPanel());
        } else if (packNumber == 5) { //blood + immunology + ABG
            allMeasurementsToTake.addAll(getHematologyPanel());
            allMeasurementsToTake.addAll(getWhiteBloodCellPanel());
            allMeasurementsToTake.addAll(getImmunologyPanel());
            allMeasurementsToTake.addAll(getArterialBloodGases());
        } else if (packNumber == 6) { //blood + ions + renal + coag + lipids + cardiac + others
            allMeasurementsToTake.addAll(getHematologyPanel());
            allMeasurementsToTake.addAll(getWhiteBloodCellPanel());
            allMeasurementsToTake.addAll(getCoagulationPanel());
            allMeasurementsToTake.addAll(getIonsAndTraceMetals());
            allMeasurementsToTake.addAll(getRenalFunctionPanel());
            allMeasurementsToTake.addAll(getLipidProfile());
            allMeasurementsToTake.addAll(getCardiacPanel());
            allMeasurementsToTake.addAll(getOtherTests());
        }
        return allMeasurementsToTake;
    }

    //methods to collect random values for each blood test pack
    private ArrayList<Measurement> getHematologyPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.HEMOGLOBIN,
                getRandomValue(Measurement.MeasurementCode.HEMOGLOBIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.HEMATOCRIT,
                getRandomValue(Measurement.MeasurementCode.HEMATOCRIT))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MCV_MEAN_CELL_VOLUME,
                getRandomValue(Measurement.MeasurementCode.MCV_MEAN_CELL_VOLUME))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MCHC_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION,
                getRandomValue(Measurement.MeasurementCode.MCHC_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION))
        );

        return measurements;
    }
    private ArrayList<Measurement> getWhiteBloodCellPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.LEUKOCYTES,
                getRandomValue(Measurement.MeasurementCode.LEUKOCYTES))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.NEUTROPHILS,
                getRandomValue(Measurement.MeasurementCode.NEUTROPHILS))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.LYMPHOCYTES,
                getRandomValue(Measurement.MeasurementCode.LYMPHOCYTES))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MONOCYTES,
                getRandomValue(Measurement.MeasurementCode.MONOCYTES))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.EOSINOPHILS,
                getRandomValue(Measurement.MeasurementCode.EOSINOPHILS))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.BASOPHILS,
                getRandomValue(Measurement.MeasurementCode.BASOPHILS))
        );
        return measurements;
    }
    private ArrayList<Measurement> getCoagulationPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.THROMBOCYTES,
                getRandomValue(Measurement.MeasurementCode.THROMBOCYTES))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MPV_MEAN_PLATELET_VOLUME,
                getRandomValue(Measurement.MeasurementCode.MPV_MEAN_PLATELET_VOLUME))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.PT_PROTHROMBIN_TIME,
                getRandomValue(Measurement.MeasurementCode.PT_PROTHROMBIN_TIME))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.APTT_ACTIVATED_PARTIAL_THROMBOPLASTIN_TIME,
                getRandomValue(Measurement.MeasurementCode.APTT_ACTIVATED_PARTIAL_THROMBOPLASTIN_TIME))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.INR,
                getRandomValue(Measurement.MeasurementCode.INR))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.ANTITHROMBIN,
                getRandomValue(Measurement.MeasurementCode.ANTITHROMBIN))
        );
        return measurements;
    }
    private ArrayList<Measurement> getImmunologyPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.CRP_C_REACTIVE_PROTEIN,
                getRandomValue(Measurement.MeasurementCode.CRP_C_REACTIVE_PROTEIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.ESR_ERYTHROCYTE_SEDIMENTATION_RATE,
                getRandomValue(Measurement.MeasurementCode.ESR_ERYTHROCYTE_SEDIMENTATION_RATE))
        );
        return measurements;
    }
    private ArrayList<Measurement> getIonsAndTraceMetals() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.SODIUM,
                getRandomValue(Measurement.MeasurementCode.SODIUM))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.POTASSIUM,
                getRandomValue(Measurement.MeasurementCode.POTASSIUM))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CHLORIDE,
                getRandomValue(Measurement.MeasurementCode.CHLORIDE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CALCIUM_IONIZED,
                getRandomValue(Measurement.MeasurementCode.CALCIUM_IONIZED))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CALCIUM_TOTAL,
                getRandomValue(Measurement.MeasurementCode.CALCIUM_TOTAL))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MAGNESIUM,
                getRandomValue(Measurement.MeasurementCode.MAGNESIUM))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.PHOSPHATE,
                getRandomValue(Measurement.MeasurementCode.PHOSPHATE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.IRON_TOTAL_SERUM,
                getRandomValue(Measurement.MeasurementCode.IRON_TOTAL_SERUM))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.TIBC_TOTAL_IRON_BINDING_CAPACITY,
                getRandomValue(Measurement.MeasurementCode.TIBC_TOTAL_IRON_BINDING_CAPACITY))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.TRANSFERRIN,
                getRandomValue(Measurement.MeasurementCode.TRANSFERRIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.TRANSFERRIN_SATURATION,
                getRandomValue(Measurement.MeasurementCode.TRANSFERRIN_SATURATION))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.FERRITIN,
                getRandomValue(Measurement.MeasurementCode.FERRITIN))
        );
        return measurements;
    }
    private ArrayList<Measurement> getLiverFunctionPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.ALBUMIN,
                getRandomValue(Measurement.MeasurementCode.ALBUMIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.BILIRRUBIN_TOTAL,
                getRandomValue(Measurement.MeasurementCode.BILIRRUBIN_TOTAL))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.BILIRRUBIN_CONJUGATED,
                getRandomValue(Measurement.MeasurementCode.BILIRRUBIN_CONJUGATED))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.ALAT_ALANINE_TRANSAMINASE,
                getRandomValue(Measurement.MeasurementCode.ALAT_ALANINE_TRANSAMINASE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.ASAT_ASPARTATE_TRANSAMINASE,
                getRandomValue(Measurement.MeasurementCode.ASAT_ASPARTATE_TRANSAMINASE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.ALP_ALKALINE_PHOSPHATASE,
                getRandomValue(Measurement.MeasurementCode.ALP_ALKALINE_PHOSPHATASE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.GGT_GAMA_GLUTAMYL_TRANSFERASE,
                getRandomValue(Measurement.MeasurementCode.GGT_GAMA_GLUTAMYL_TRANSFERASE))
        );
        return measurements;
    }
    private ArrayList<Measurement> getRenalFunctionPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.UREA,
                getRandomValue(Measurement.MeasurementCode.UREA))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CREATININE,
                getRandomValue(Measurement.MeasurementCode.CREATININE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CREATININE_CLEARANCE,
                getRandomValue(Measurement.MeasurementCode.CREATININE_CLEARANCE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.BUN_CREATININE_RATIO,
                getRandomValue(Measurement.MeasurementCode.BUN_CREATININE_RATIO))
        );
        return measurements;
    }
    private ArrayList<Measurement> getHormonesPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.TSH,
                getRandomValue(Measurement.MeasurementCode.TSH))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.T4_FREE_THYROXIN,
                getRandomValue(Measurement.MeasurementCode.T4_FREE_THYROXIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.T4_TOTAL_THYROXIN,
                getRandomValue(Measurement.MeasurementCode.T4_TOTAL_THYROXIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.T3_FREE_TRIIODOTHYRONINE,
                getRandomValue(Measurement.MeasurementCode.T3_FREE_TRIIODOTHYRONINE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.TBG_THYROID_BINDING_GLOBULIN,
                getRandomValue(Measurement.MeasurementCode.TBG_THYROID_BINDING_GLOBULIN))
        );
        return measurements;
    }
    private ArrayList<Measurement> getLipidProfile() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.TRIGLYCERIDES,
                getRandomValue(Measurement.MeasurementCode.TRIGLYCERIDES))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CHOLESTEROL_TOTAL,
                getRandomValue(Measurement.MeasurementCode.CHOLESTEROL_TOTAL))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.HDL_CHOLESTEROL,
                getRandomValue(Measurement.MeasurementCode.HDL_CHOLESTEROL))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.LDL_CHOLESTEROL,
                getRandomValue(Measurement.MeasurementCode.LDL_CHOLESTEROL))
        );
        return measurements;
    }
    private ArrayList<Measurement> getArterialBloodGases() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.PH,
                getRandomValue(Measurement.MeasurementCode.PH))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.PO2_O2_PARTIAL_PRESSURE,
                getRandomValue(Measurement.MeasurementCode.PO2_O2_PARTIAL_PRESSURE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.PCO2_CO2_PARTIAL_PRESSURE,
                getRandomValue(Measurement.MeasurementCode.PCO2_CO2_PARTIAL_PRESSURE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.SATO2_O2_SATURATION,
                getRandomValue(Measurement.MeasurementCode.SATO2_O2_SATURATION))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.HCO3_BICARBONATE,
                getRandomValue(Measurement.MeasurementCode.HCO3_BICARBONATE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.BASE_EXCESS,
                getRandomValue(Measurement.MeasurementCode.BASE_EXCESS))
        );
        return measurements;
    }
    private ArrayList<Measurement> getCardiacPanel() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.CK_CREATINE_KINASE,
                getRandomValue(Measurement.MeasurementCode.CK_CREATINE_KINASE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.CKMB,
                getRandomValue(Measurement.MeasurementCode.CKMB))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.MYOGLOBIN,
                getRandomValue(Measurement.MeasurementCode.MYOGLOBIN))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.TROPONIN_T,
                getRandomValue(Measurement.MeasurementCode.TROPONIN_T))
        );
        return measurements;
    }
    private ArrayList<Measurement> getOtherTests() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        measurements.add(new Measurement(
                Measurement.MeasurementCode.LDH_LACTATE_DEHYDROGENASE,
                getRandomValue(Measurement.MeasurementCode.LDH_LACTATE_DEHYDROGENASE))
        );
        measurements.add(new Measurement(
                Measurement.MeasurementCode.D_DIMER,
                getRandomValue(Measurement.MeasurementCode.D_DIMER))
        );
        return measurements;
    }

    //generates a random value for a given measurement according to the range of normal values (allows for abnormal values)
    private double getRandomValue(Measurement.MeasurementCode code) {
        double minValue = dbManager.getMinValue(code);
        double minRange = minValue - minValue * 0.18;
        if (minRange < 0) {
            minRange = 0;
        }

        double maxValue = dbManager.getMaxValue(code);
        double maxRange = maxValue + maxValue * 0.18;

        Random rnd = new Random();
        double randomValue = minRange + maxRange * rnd.nextDouble();
        return randomValue;
    }

}
