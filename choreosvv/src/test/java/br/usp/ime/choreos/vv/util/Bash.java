package br.usp.ime.choreos.vv.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Bash {
	
	public static void deployService() {
		Runtime proc = Runtime.getRuntime();
		try {
			proc.exec("java -jar resources/store.jar");
			Thread.sleep(3000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void undeployService() {

		Process proc;
		try {
			proc = Runtime.getRuntime().exec("/bin/bash", null, new File("."));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(proc.getOutputStream()), 1024), true);
			out.println("ps aux | grep \"java -jar resources/store.jar"
					+ "\" | grep -v grep | awk '{print $2}' > a; kill -9 $(cat a); rm a");
			out.println("exit");
			proc.waitFor();
			in.close();
			out.close();
			proc.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		Bash.deployService();
	}

}
