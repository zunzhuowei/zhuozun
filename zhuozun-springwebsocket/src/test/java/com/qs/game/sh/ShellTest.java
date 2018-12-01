package com.qs.game.sh;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zun.wei on 2018/11/29 10:33.
 * Description: test for execute for shell
 */
public class ShellTest {


    @Test
    public void deployWar() throws IOException {
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        String[] remote_server_names = serverEnum.remote_server_name; //远程服务器名称

        // 远程执行ssh 备份上次部署的war包添加一个日期
        boolean isBackups = serverEnum.isBackups;
        if (isBackups) {
            builder.append("echo ").append("----------- Remote execution of SSH last backup war ------------").append("\n");
            for (String remote_server_name : remote_server_names) {
                builder.append("ssh ").append(remote_server_name).append(" << ").append("remotessh").append("\n")
                        .append("cd ").append(serverEnum.remote_tomcat_path).append("\n");
                //备份 上一次部署的 war 包
                builder.append("cd ").append("webapps").append("/\n")
                        .append("cp -r ").append(serverEnum.warPackageName).append(" ")
                        .append(serverEnum.warPackageName)
                        .append(simpleDateFormat.format(new Date())).append("\n")
                        .append("cd ").append(serverEnum.remote_tomcat_path).append("\n");
                builder.append("echo ").append("----------- finish  last backup war on remote server ")
                        .append(remote_server_name).append(" ------------").append("\n")
                        .append("exit").append("\n")
                        .append("remotessh").append("\n");
            }
        }

        // 打依赖包
        String[] dependencyArtifactIds = serverEnum.dependencyArtifactIds;
        for (String artifact : dependencyArtifactIds) {
            builder.append("echo ").append("----------- install artifact ").append(artifact).append(" --------").append("\n");
            builder.append("cd ").append(ServerEnum.java_source_path).append("/").append(artifact).append("/\n");
            builder.append("mvn clean install -DtestSkip").append("\n");
        }

        //创建临时打包位置
        builder.append("echo ").append("----------- Create a temporary packaging location ------------").append("\n");
        builder.append("mkdir -p ").append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //前往java源文件目录
        builder.append("echo ").append("----------- Go to the Java source directory ------------").append("\n");
        builder.append("cd ").append(ServerEnum.java_source_path).append("\n");

        //复制要打包的应用到临时打包目录中
        builder.append("echo ").append("----------- Copy the application to be packaged into the temporary packaging directory ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.artifactId).append("/src").append(" ")
                .append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //复制要打包的应用的pom.xml到临时打包目录中
        builder.append("echo ").append("----------- Copy the pom.xml of the application to be packaged into the temporary packaging directory ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.artifactId).append("/pom.xml").append(" ")
                .append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        // 前往临时打包路径
        builder.append("echo ").append("----------- To temporary packing path ------------").append("\n");
        builder.append("cd ").append(ServerEnum.packageTempPath).append("/").append(serverEnum.artifactId).append("\n");

        //拷贝properties到要打包的src/resources中
        builder.append("echo ").append("----------- Copy properties to src/resources to be packaged ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.config_file_dir).append(" ").append(ServerEnum.packageTempPath)
                .append("/").append(serverEnum.artifactId).append("/src/main/resources/").append("\n");

        //拷贝spring.xml到要打包的src/resources/spring中
        builder.append("echo ").append("----------- Copy spring.xml to src/resources/spring to be packaged ------------").append("\n");
        builder.append("cp -r ").append(serverEnum.config_file_dir_xml).append(" ").append(ServerEnum.packageTempPath)
                .append("/").append(serverEnum.artifactId).append("/src/main/resources/spring/").append("\n");

        //打war应用包
        builder.append("echo ").append("----------- War application package ------------").append("\n");
        builder.append("mvn clean package -Dmaven.test.skip").append("\n");

        //将war包移动到上级目录，并修改war包名字
        builder.append("echo ").append("----------- Move the war package to the superior directory and modify the name of the war package ------------").append("\n");
        builder.append("mv target/*.war ").append("./").append(serverEnum.warPackageName).append("\n");

        //上传war 到远程服务器
        builder.append("echo ").append("----------- Upload war to remote server ------------").append("\n");

        for (String remote_server_name : remote_server_names) {
            builder.append("scp -r ").append(serverEnum.warPackageName).append(" ").append(remote_server_name)
                    .append(":").append(serverEnum.remote_tomcat_path).append("\n");
        }

        // 远程执行ssh 把上上传的war包mv到tomcat webapps中
        builder.append("echo ").append("----------- Remote execution of SSH to upload the war package MV to Tomcat webapps ------------").append("\n");
        for (String remote_server_name : remote_server_names) {
            builder.append("ssh ").append(remote_server_name).append(" << ").append("remotessh").append("\n")
                    .append("cd ").append(serverEnum.remote_tomcat_path).append("\n");
            builder.append("mv ").append(serverEnum.warPackageName).append(" ").append("webapps").append("\n")
                    .append("echo ").append("----------- finish deploy war to server ").append(remote_server_name).append(" ------------").append("\n")
                    .append("exit").append("\n")
                    .append("remotessh").append("\n");
        }

        // 回到临时打包位置
        builder.append("cd ").append(ServerEnum.packageTempPath).append("/").append("\n");
        // 删除src war 临时文件
        builder.append("rm -rf ").append(serverEnum.artifactId).append("\n");

        // mvn clean 依赖包
        for (String artifact : dependencyArtifactIds) {
            builder.append("echo ").append("----------- remove dependency artifact ").append(artifact).append(" --------").append("\n");
            builder.append("cd ").append(ServerEnum.java_source_path).append("/").append(artifact).append("/\n");
            builder.append("rm -rf target").append("\n");
        }

        ps.println(builder.toString());// 往文件里写入字符串
        ps.close();
        // 脚本文件目录
        String commandStr = ServerEnum.gitPath + " " + file.getAbsolutePath();
        ShellTest.exeCmd(commandStr);
        // 执行完脚本后，删除脚本临时文件。
        boolean del = file.delete();
        System.out.println("del = " + del);
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
