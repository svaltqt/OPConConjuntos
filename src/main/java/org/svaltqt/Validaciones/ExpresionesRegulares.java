package org.svaltqt.Validaciones;

import java.util.regex.Pattern;

public class ExpresionesRegulares {
    public static Pattern cedulaPattern = Pattern.compile("^\\d{5}$");
    public static Pattern fechaPattern = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[0-2])-(\\d{4})$");
    public static Pattern nombrePattern = Pattern.compile("^[A-Z ]+$");

    public static Pattern numAsignaturasPattern = Pattern.compile("^[1-9]|10$");
    public static Pattern numHorasPattern = Pattern.compile("^(1?[0-9]|20)$");


}
