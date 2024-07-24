package mx.gob.tecdmx.seguridad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

public class SeguridadUtils {
	
	public String objectToJson(DTOResponse objeto) {
		Gson gson = new Gson();
		String json = gson.toJson(objeto);
		return json;
	}
	
	public Date formatDate(String fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(fecha);
            return date;
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
            // Handle the error as necessary
        }
        return null;
	}
	
	public String formatDate(Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaUTC = formatter.format(fecha);
        fechaUTC = fechaUTC.replaceAll(":", "");
        return fechaUTC;
	}

}
