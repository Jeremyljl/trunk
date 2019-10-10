
/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

import java.io.*;
import java.util.*;

public class PackageDeliverySystem {
	public ArrayList<Package> packages = new ArrayList<Package>();
	public Block[][] blocks;

	public void arrangePackage() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				blocks[i][j].ArrangeSpecial();
				blocks[i][j].ArrangeRegular();
			}
		}
	}

	public PackageDeliverySystem() {
		blocks = new Block[9][3];
		for (int i = 0; i < 9; i++) {
			blocks[i][0] = new Block();
			blocks[i][1] = new Block();
			blocks[i][2] = new Block();
		}
	}

	public static void main(String[] args) {

		PackageDeliverySystem pds = new PackageDeliverySystem();
		
		// creat a random input file
		//if you want to check your sample input file ,you need 
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter number of packages you want to create: ");
		int count = scanner.nextInt();
		String[] companies = { "Orange Inc.", "HAL Industries", "Macrohard Inc.", "Giggle Industries",
				"Peak Enterprises", "Macrohard Inc." };
		int companiesLen = companies.length;
		Random random = new Random();
		try {
			File direction = new File("resource/");
			File file = new File("resource/Package.txt");
			
			FileOutputStream out = new FileOutputStream(file);
			BufferedOutputStream bufOut = new BufferedOutputStream(out);
			Date date = new Date();
			for (int i = 0; i < count; i++) {
				StringBuilder sb = new StringBuilder();
				int type = random.nextInt() % 2;
				if (type == 0) {
					sb.append("S,");
				} else {
					sb.append("R,");
				}
				int companyIndex = Math.abs(random.nextInt(companiesLen));
				sb.append(companies[companyIndex]).append(",");
				char row = (char) (Math.abs(random.nextInt(26)) + 'A');
				int col = Math.abs(random.nextInt(9)) + 1;
				sb.append(row).append(col).append(",");

				int day = Math.abs(random.nextInt()) % 2 + date.getDay();
				int month = date.getMonth() + 1;
				int year = date.getYear() + 1900;
				sb.append(day).append("/").append(month).append("/").append(year).append(",");

				int vol = Math.abs(random.nextInt()) % 300 + 1;
				int weight = Math.abs(random.nextInt()) % 300 + 1;
				sb.append(vol).append(",").append(weight);
				if (type == 0) {
					int time = Math.abs(random.nextInt()) % (16 - 9) + 9;
					sb.append(",").append(time);
				}
				sb.append("\n");
				bufOut.write(sb.toString().getBytes());
			}
			System.out.println("\n Please look at Resource Folder, your random .txt file has been created. \n");
			System.out.println("\n The following is the truck information from packages1.txt file is: \n");
			bufOut.flush();
			bufOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
//  read file-->resource/packages1.txt
		try {
			FileReader fr = new FileReader("resource/packages1.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			String[] arrs = null;
			String[] date = null;
			while ((line = br.readLine()) != null) {
				arrs = line.split(",");
				// System.out.println(line);
				Package p = null;
				int row = arrs[2].charAt(0) - 'A';
				int col = arrs[2].charAt(1) - '1';
				date = arrs[3].split("/");
				if (arrs[0].equals("R")) {
					p = new Package(arrs[1], arrs[2],
							new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])),
							Integer.parseInt(arrs[4]), Integer.parseInt(arrs[5]));
					pds.blocks[row / 3][col / 3].packages.add(pds.blocks[row / 3][col / 3].indexSP, p);
					pds.blocks[row / 3][col / 3].arranged.add(false);
					pds.blocks[row / 3][col / 3].indexSP++;
				} else if (arrs[0].equals("S")) {
					p = new SpecialPackage(arrs[1], arrs[2],
							new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])),
							Integer.parseInt(arrs[4]), Integer.parseInt(arrs[5]), Integer.parseInt(arrs[6]));
					pds.blocks[row / 3][col / 3].packages.add((SpecialPackage) p);
				}
				pds.packages.add(p);
			}

			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		pds.arrangePackage();
//write file --> resource/Deliveries.txt
		int small = 0, middle = 0, large = 0;
		try {
			FileWriter fw = new FileWriter("resource/Deliveries.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < pds.blocks[i][j].trucks.size(); k++) {
						switch (pds.blocks[i][j].trucks.get(k).getType()) {
						case 1:
							small++;
							break;
						case 2:
							middle++;
							break;
						case 3:
							large++;
							break;
						}
						bw.write(pds.blocks[i][j].trucks.get(k).toString() + "\n");
						for (int m = 0; m < pds.blocks[i][j].trucks.get(k).getPackageArrayList().size(); m++) {
							bw.write("  " + pds.blocks[i][j].trucks.get(k).getPackageArrayList().get(m).toString()
									+ "\n");
						}
					}
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		System.out.println("number of small truck:" + small);
		System.out.println("number of middle truck:" + middle);
		System.out.println("number of large truck:" + large);
		System.out.println("number of total truck hours:" + (small + middle * 2 + large * 3));

	}
}
