
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.nio.file.Paths;

public class GenerateQRCode {
 
    public static void main(String[] args) throws Exception {
         
        String content = "Here Past the URL of the website you want to Generate QR code for it.";
        String pathToStore = "C:\\Users\\Abdul Waris Sherzad\\OneDrive\\Pictures\\QRCodeGenerated.jpg";
         
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", Paths.get(pathToStore));
         
        System.out.println("QR Code Generated Successfully");
 
    }
 
}