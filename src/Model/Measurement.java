package Model;

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
        MCV_MEAN_CORPUSCULAR_VOLUME(3),
        MCH(4),

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
            //ANEMIA STUDY!
        IRON_TOTAL_SERUM(25),
        TIBC_TOTAL_IRON_BINDING_CAPACITY(26),
        TRANSFERRIN(27),
        TRANSFERRIN_SATURATION(28),
        FERRITIN(29),

        //LIVER FUNCTION
        ALBUMIN(30),
        BILIRRUBIN_TOTAL(31),
        BILIRRUBIN_CONJUGATED(32),
        ALAT_ALANINE_TRANSAMINASE(33),
        ASAT_ASPARTATE_TRANSAMINASE(34),
        ALP_ALKALINE_PHOSPHATASE(35),
        GGT_GAMA_GLUTAMYL_TRANSFERASE(36),

        //RENAL FUNCTION
        UREA(37),
        CREATININE(38),
        CREATININE_CLEARANCE(39),
        BUN_CREATININE_RATIO(40),

        //HORNOMES
        TSH(41),
        T4_FREE_THYROXIN(42),
        T4_TOTAL_THYROXIN(43),
        T3_FREE_TRIIODOTHYRONINE(44),
        TBG_THYROID_BINDING_GLOBULIN(45),

        //LIPID PROFILE
        TRIGLYCERIDES(46),
        CHOLESTEROL_TOTAL(47),
        HDL_CHOLESTEROL(48),
        LDL_CHOLESTEROL(49),

        //ARTERIAL BLOOD GASES
        PH(50),
        PO2_O2_PARTIAL_PRESSURE(51),
        PCO2_CO2_PARTIAL_PRESSURE(52),
        SATO2_O2_SATURATION(53),
        HCO3_BICARBONATE(54),
        BASE_EXCESS(55),

        //CARDIAC TESTS
        CK_CREATINE_KINASE(56),
        CKMB(57),
        MYOGLOBIN(58),
        TROPONIN_T(59),

        //OTHERS
        LDH_LACTATE_DEHYDROGENASE(60),
        D_DIMER(61);

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

}
