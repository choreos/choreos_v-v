package eu.choreos.vv.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import eu.choreos.vv.data.ScalabilityReport;

public class SaveToFile extends Analyzer {

	private File file;
	private boolean append;

	public SaveToFile(File file, boolean append) {
		this.file = file;
		this.append = append;
	}

	public SaveToFile(File file) {
		this(file, false);
	}
	
	@Override 
	public void analyse(ScalabilityReport report) throws Exception {
		save(report);
	}

	@Override
	public void analyse(List<ScalabilityReport> reports)
			throws IOException {
		save(reports);
	}

	private void save(Object item)
			throws FileNotFoundException, IOException {
		OutputStream os = new FileOutputStream(file, append);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(item);
		oos.close();
		os.close();
	}

}
