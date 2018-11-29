package com.qs.game.sh;

import com.qs.game.SpringWebSocketApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by zun.wei on 2018/11/29 10:33.
 * Description: test for execute for shell
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringWebSocketApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShellTest {


    @Test
    public void test01() throws IOException {
        // 选择要发布的应用
        ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_WEB_HAPPY_GAME);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_ACTI_HAPPY_GAME);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_APP_HAPPY_GAME);

        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_WEB_HAPPY_BEARD);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_ACTI_HAPPY_BEARD);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.TEST_SERVER_APP_HAPPY_BEARD);


        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_WEB_HAPPY_GAME);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_ACTI_HAPPY_GAME);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_APP_HAPPY_GAME);

        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_WEB_HAPPY_BEARD);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_ACTI_HAPPY_BEARD);
        //ServerEnum serverEnum = ServerEnum.getServerEnum(ServerEnum.ONLINE_SERVER_APP_HAPPY_BEARD);

        File file = new File("temp.txt");
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        StringBuilder builder = new StringBuilder();

        // install qs-common
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-common/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");
        // install qs-side
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-side/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");
        // install qs-config
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-config/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");
        // install qs-model
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-model/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");
        // install qs-mapper
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-mapper/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");
        // install qs-service
        builder.append("cd ").append(ServerEnum.java_source_path).append("/").append("qs-service/").append("\n");
        builder.append("mvn clean install -DtestSkip").append("\n");

        //创建临时打包位置
        builder.append("echo ").append("----------- 创建临时打包位置 ------------").append("\n");
        builder.append("mkdir -p ").append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //前往java源文件目录
        builder.append("echo ").append("----------- 前往java源文件目录 ------------").append("\n");
        builder.append("cd ").append(ServerEnum.java_source_path).append("\n");

        //复制要打包的应用到临时打包目录中
        builder.append("echo ").append("----------- 复制要打包的应用到临时打包目录中 ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.artifactId).append("/src").append(" ")
                .append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //复制要打包的应用到临时打包目录中
        builder.append("echo ").append("----------- 复制要打包的应用到临时打包目录中 ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.artifactId).append("/pom.xml").append(" ")
                .append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        // 前往临时打包路径
        builder.append("echo ").append("----------- 前往临时打包路径 ------------").append("\n");
        builder.append("cd ").append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //拷贝properties到要打包的src/resources中
        builder.append("echo ").append("----------- 拷贝properties到要打包的src/resources中 ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.config_file_dir).append(" ").append(ServerEnum.packageTempPath)
                .append("/").append(serverEnum.artifactId).append("/src/main/resources/").append("\n");

        //拷贝spring.xml到要打包的src/resources/spring中
        builder.append("echo ").append("----------- 拷贝spring.xml到要打包的src/resources/spring中 ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.config_file_dir_xml).append(" ").append(ServerEnum.packageTempPath)
                .append("/").append(serverEnum.artifactId).append("/src/main/resources/spring/").append("\n");

        //打war应用包
        builder.append("echo ").append("----------- 打war应用包 ------------").append("\n");
        builder.append("mvn clean package -Dmaven.test.skip").append("\n");

        //将war包移动到上级目录，并修改war包名字
        builder.append("echo ").append("----------- 将war包移动到上级目录，并修改war包名字 ------------").append("\n");
        builder.append("mv target/*.war ").append("./").append(serverEnum.warPackageName).append("\n");

        //上传war 到远程服务器
        builder.append("echo ").append("----------- 上传war 到远程服务器 ------------").append("\n");
        String[] remote_server_names = serverEnum.remote_server_name;
        for (String remote_server_name : remote_server_names) {
            builder.append("scp -r ").append(serverEnum.warPackageName).append(" ").append(remote_server_name)
                    .append(":").append(serverEnum.remote_tomcat_path).append("\n");
        }

        // 远程执行ssh 把上上传的war包mv到tomcat webapps中
        builder.append("echo ").append("----------- 远程执行ssh 把上上传的war包mv到tomcat webapps中 ------------").append("\n");
        for (String remote_server_name : remote_server_names) {
            builder.append("ssh ").append(remote_server_name).append(" << ").append("remotessh").append("\n")
                    .append("cd ").append(serverEnum.remote_tomcat_path).append("\n")
                    .append("mv ").append(serverEnum.warPackageName).append(" ").append("webapps").append("\n")
                    .append("echo ").append("-----------finish deploy war to server ------------").append("\n")
                    .append("exit").append("\n")
                    .append("remotessh").append("\n");
        }

        // 回到临时打包位置
        builder.append("cd ").append(ServerEnum.packageTempPath).append("/").append("\n");

        ps.println(builder.toString());// 往文件里写入字符串
        ps.close();
        // 脚本文件目录
        String commandStr = ServerEnum.gitPath + " " + file.getAbsolutePath();
        ShellTest.exeCmd(commandStr);
        //boolean del = file.delete();
        //System.out.println("del = " + del);
    }


    private static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
