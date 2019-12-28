package model;

public class Measurement {

    private int measurementCode;
    private double value;
    //talvez não precise nunca dos min_value e max_value a nível de código...?
    private double min_value;
    private double max_value;
    //mesma coisa com as unidades?...

    public enum MeasurementCode {
        //HEMATOLOGY
        HEMOGLOBIN(1),
        HEMATOCRIT(2),
        MCV_MEAN_CELL_VOLUME(3),
        MCHC_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION(4),

        //WHITE BLOOD CELLS
        LEUKOCYTES(5),
        NEUTROPHILS(6),
        LYMPHOCYTES(7),
        MONOCYTES(8),
        EOSINOPHILS(9),
        BASOPHILS(10),

        //COAGULATION
        THROMBOCYTES(11),
        MPV_MEAN_PLATELET_VOLUME(12),
        PT_PROTHROMBIN_TIME(13),
        APTT_ACTIVATED_PARTIAL_THROMBOPLASTIN_TIME(14),
        INR(15),
        ANTITHROMBIN(16),

        //IMMUNOLOGY - ACUTE PHASE PROTEINS
        CRP_C_REACTIVE_PROTEIN(17),
        ESR_ERYTHROCYTE_SEDIMENTATION_RATE(18),

        //IONS AND TRACE METALS
        SODIUM(19),
        POTASSIUM(20),
        CHLORIDE(21),
        CALCIUM_IONIZED(22),
        CALCIUM_TOTAL(23),
        MAGNESIUM(24),
        PHOSPHATE(25),
            //ANEMIA STUDY!
        IRON_TOTAL_SERUM(26),
        TIBC_TOTAL_IRON_BINDING_CAPACITY(27),
        TRANSFERRIN(28),
        TRANSFERRIN_SATURATION(29),
        FERRITIN(30),

        //LIVER FUNCTION
        ALBUMIN(31),
        BILIRRUBIN_TOTAL(32),
        BILIRRUBIN_CONJUGATED(33),
        ALAT_ALANINE_TRANSAMINASE(34),
        ASAT_ASPARTATE_TRANSAMINASE(35),
        ALP_ALKALINE_PHOSPHATASE(36),
        GGT_GAMA_GLUTAMYL_TRANSFERASE(37),

        //RENAL FUNCTION
        UREA(38),
        CREATININE(39),
        CREATININE_CLEARANCE(40),
        BUN_CREATININE_RATIO(41),

        //HORNOMES
        TSH(42),
        T4_FREE_THYROXIN(43),
        T4_TOTAL_THYROXIN(44),
        T3_FREE_TRIIODOTHYRONINE(45),
        TBG_THYROID_BINDING_GLOBULIN(46),

        //LIPID PROFILE
        TRIGLYCERIDES(47),
        CHOLESTEROL_TOTAL(48),
        HDL_CHOLESTEROL(49),
        LDL_CHOLESTEROL(50),

        //ARTERIAL BLOOD GASES
        PH(51),
        PO2_O2_PARTIAL_PRESSURE(52),
        PCO2_CO2_PARTIAL_PRESSURE(53),
        SATO2_O2_SATURATION(54),
        HCO3_BICARBONATE(55),
        BASE_EXCESS(56),

        //CARDIAC TESTS
        CK_CREATINE_KINASE(57),
        CKMB(58),
        MYOGLOBIN(59),
        TROPONIN_T(60),

        //OTHERS
        LDH_LACTATE_DEHYDROGENASE(61),
        D_DIMER(62);

        private int id;

        MeasurementCode(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }


    public Measurement(MeasurementCode measurementCode, double value) {
        this.measurementCode = measurementCode.getId();
        this.value = value;
    }

    public int getMeasurementCode() {
        return measurementCode; //returns the ID
    }


    public double getValue() {
        return value;
    }
}
