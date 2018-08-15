/**
 * IPBin - The private encrpyted IPFS Bin
 * @author BowStar
 * @version 1.0
 */

package ml.uniwide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Scanner;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multibase.Base58;
import io.ipfs.multihash.Multihash;

public class IPBin {

	public static void main(String[] args) {
		IPBin ip = new IPBin();
		Crypto crypto = new Crypto();
		
		// use the user input
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print(">");
			if(scan.hasNextLine()) {
				String command = scan.nextLine();
				
				// upload command
				if(command.startsWith("upload ")) {
					String key = crypto.generateKey();
					File file = new File(command.substring(7));
					String msg = null;
					String tmp = System.getProperty("java.io.tmpdir");
					Path path = Paths.get(tmp, "encrypted.ipbin");
					
					try {
						// encode file
						msg = new String(Files.readAllBytes(Paths.get(file.toURI())));
						msg = crypto.encodeMessage(key, msg);
						
						// create encoded file in tmp
						Files.deleteIfExists(path);
						try(BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE)){
							bw.write(msg);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.out.println("Your key: " + key);
					System.out.println("Hash: " + ip.uploadFile(path.toString()));
				}
				
				// open command
				else if(command.startsWith("open ")) {
					System.out.print("key: ");
					String key = scan.nextLine();
					String hash = command.substring(5);
					
					ip.printFileContent(hash, key);
				}
			}
		}
	}
	
	// upload file to IPFS
	public String uploadFile(String dir) {
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(dir));
		try {
			MerkleNode addResult = ipfs.add(file).get(0);
			return addResult.hash.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// print the content of the file
	public void printFileContent(String hash, String key) {
		Crypto crypto = new Crypto();
		
		URL url;
		try {
			url = new URL("https://ipfs.io/ipfs/" + hash);
			Scanner s = new Scanner(url.openStream());
			while(s.hasNextLine()) {
				System.out.println(crypto.decodeMessage(key, s.nextLine()));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
