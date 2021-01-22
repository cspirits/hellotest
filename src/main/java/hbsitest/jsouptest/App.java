package hbsitest.jsouptest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{

	private static OutputStream os;
 
	public static void main(String[] args) {
		try {
			//添加配置
			Document doc = Jsoup.connect("https://www.csdn.net/").get();
//			System.out.println(doc.title()); //CSDN-专业IT技术社区
			//把文章标题和连接写入txt文件
			Element feedlist_id = doc.getElementById("feedlist_id");
			Elements h2 = feedlist_id.select("h2.csdn-tracking-statistics");
			Elements a = h2.select("a");
			//指定文件名及路径
			File file = new File("D:\\jsoup\\word\\test.txt"); 
			if (!file.exists()) {
				file.createNewFile();
			}
			//写入本地
			PrintWriter pw = new PrintWriter("D:\\jsoup\\word\\test.txt","UTF-8"); 
			for (Element element : a) {
				pw.println(element.text());
				pw.println(element.attr("href")); 
				pw.println("------------------------------------------------------------------------------------------------------------------------------------");
			}
			pw.close(); //关闭输出流
			//获取页面上的图片保存到本地
			Elements imgs = doc.select("img[src$=.png]");
			for (Element element : imgs) {
				String img = element.attr("src");
				String url = "http:"+img;
				System.out.println(url);
				System.out.println(url.indexOf("csdn"));
				if (url.indexOf("csdn")==-1) {
					continue;
				}
				URL u = new URL(url);
				URLConnection uc=u.openConnection();
		        //获取数据流
		        InputStream is=uc.getInputStream();
		        //获取后缀名
		        String imageName = img.substring(img.lastIndexOf("/") + 1,img.length());
		        //写入本地
		        os = new FileOutputStream(new File("D:\\jsoup\\img", imageName));
		        byte[] b = new byte[1024];
		        int i=0;
		        while((i=is.read(b))!=-1){
		          os.write(b, 0, i);
		        }
		        is.close();
		        os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
