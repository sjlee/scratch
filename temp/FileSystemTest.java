import java.nio.file.FileSystems;
import java.util.Set;

public class FileSystemTest {
  public static void main(String[] args) {
    Set<String> views = FileSystems.getDefault().supportedFileAttributeViews();
	for (String view: views) {
	  System.out.print(view + " ");
	}
	System.out.println();
  }
}