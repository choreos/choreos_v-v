package eu.choreos.vv.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import eu.choreos.vv.data.ScalabilityReport;

public class SaveToFile implements Analyser {

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
	public void analyse(List<ScalabilityReport> reports)
			throws IOException {
		OutputStream os = new FileOutputStream(file, append);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(reports);
		oos.close();
		os.close();
	}

}
