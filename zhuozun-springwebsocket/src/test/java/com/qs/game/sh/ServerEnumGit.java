package com.qs.game.sh;

/**
 * Created by zun.wei on 2018/11/29 15:58.
 * Description:
 */
public enum ServerEnumGit {


    // 跑得快金币场测试服 web 后台
    TEST_SERVER_WEB_HAPPY_GAME(
            "qs-web",
            "happyweb.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happygame/web/*.properties",
            "/E/qs_game_cfg_files/test/happygame/web/spring-applicationContext.xml"
    ),

    // 跑得快金币场测试服 app 后台
    TEST_SERVER_APP_HAPPY_GAME(
            "qs-app",
            "happyapp.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happygame/app/*.properties",
            "/E/qs_game_cfg_files/test/happygame/app/spring-applicationContext.xml"
    ),

    // 跑得快金币场测试服 acti 后台
    TEST_SERVER_ACTI_HAPPY_GAME(
            "qs-activity-center",
            "happyacti.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happygame/acti/*.properties",
            "/E/qs_game_cfg_files/test/happygame/acti/spring-applicationContext.xml"
    ),

    // 跑胡子金币场测试服 web 后台
    TEST_SERVER_WEB_HAPPY_BEARD(
            "qs-web",
            "happybeardweb.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happybeard/web/*.properties",
            "/E/qs_game_cfg_files/test/happybeard/web/spring-applicationContext.xml"
    ),

    // 跑胡子金币场测试服 app 后台
    TEST_SERVER_APP_HAPPY_BEARD(
            "qs-app",
            "happybeardapp.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happybeard/app/*.properties",
            "/E/qs_game_cfg_files/test/happybeard/app/spring-applicationContext.xml"
    ),

    // 跑胡子金币场测试服 acti 后台
    TEST_SERVER_ACTI_HAPPY_BEARD(
            "qs-activity-center",
            "happybeardacti.war",
            new String[]{"testWebServer"},
            false,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/test/happybeard/acti/*.properties",
            "/E/qs_game_cfg_files/test/happybeard/acti/spring-applicationContext.xml"
    ),



    //////////////////////////////  online  //////////////////////////////////////////////////////



    // 跑得快金币场正式服 web 后台
    ONLINE_SERVER_WEB_HAPPY_GAME(
            "qs-web",
            "happyweb.war",
            new String[]{"miniBeardWeb"},
            true,
            "/wwwroot/java/tomcat-jxgame/",
            "/E/qs_game_cfg_files/online/happygame/web/*.properties",
            "/E/qs_game_cfg_files/online/happygame/web/spring-applicationContext.xml"
    ),

    // 跑得快金币场正式服 app 后台
    ONLINE_SERVER_APP_HAPPY_GAME(
            "qs-app",
            "happyapp.war",
            new String[]{"miniBeardApp01", "miniBeardApp02", "miniBeardApp03"},
            true,
            "/wwwroot/java/tomcat-gold/",
            "/E/qs_game_cfg_files/online/happygame/app/*.properties",
            "/E/qs_game_cfg_files/online/happygame/app/spring-applicationContext.xml"
    ),

    // 跑得快金币场正式服 acti 后台
    ONLINE_SERVER_ACTI_HAPPY_GAME(
            "qs-activity-center",
            "happyacti.war",
            new String[]{"miniBeardApp01", "miniBeardApp02", "miniBeardApp03"},
            true,
            "/wwwroot/java/tomcat-gold/",
            "/E/qs_game_cfg_files/online/happygame/acti/*.properties",
            "/E/qs_game_cfg_files/online/happygame/acti/spring-applicationContext.xml"
    ),

    // 跑胡子金币场正式服 web 后台
    ONLINE_SERVER_WEB_HAPPY_BEARD(
            "qs-web",
            "happybeardweb.war",
            new String[]{"qsBeardWeb"},
            true,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/online/happybeard/web/*.properties",
            "/E/qs_game_cfg_files/online/happybeard/web/spring-applicationContext.xml"
    ),

    // 跑胡子金币场正式服 app 后台
    ONLINE_SERVER_APP_HAPPY_BEARD(
            "qs-app",
            "happybeardapp.war",
            new String[]{"qsBeardApp01", "qsBeardApp02"},
            true,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/online/happybeard/app/*.properties",
            "/E/qs_game_cfg_files/online/happybeard/app/spring-applicationContext.xml"
    ),

    // 跑胡子金币场正式服 acti 后台
    ONLINE_SERVER_ACTI_HAPPY_BEARD(
            "qs-activity-center",
            "happybeardacti.war",
            new String[]{"qsBeardApp01", "qsBeardApp02"},
            true,
            "/wwwroot/java/tomcat-happy/",
            "/E/qs_game_cfg_files/online/happybeard/acti/*.properties",
            "/E/qs_game_cfg_files/online/happybeard/acti/spring-applicationContext.xml"
    ),
    ;


    public static final String gitPath = "D:/Git/git-bash.exe";//git安装目录
    public static final String packageTempPath = "/D/packageTempPath"; //临时构建目录
    public static final String java_source_path = "/D/idea_poject/mini"; //java项目源文件目录

    ServerEnumGit(String artifactId, String warPackageName, String[] remote_server_name, boolean isBackups,
                  String remote_tomcat_path, String config_file_dir, String config_file_dir_xml) {

        this.artifactId = artifactId;
        this.warPackageName = warPackageName;
        this.remote_server_name = remote_server_name;
        //this.dependencyArtifactIds = dependencyArtifactIds;
        this.isBackups = isBackups;
        this.remote_tomcat_path = remote_tomcat_path;
        this.config_file_dir = config_file_dir;
        this.config_file_dir_xml = config_file_dir_xml;
    }

    public String artifactId; //maven项目中的应用名称
    public String warPackageName; //war包的名字
    public String[] remote_server_name; //远程服务器名称（匹配ssh免密别名登录名称）
    public String remote_tomcat_path; //远程服务器tomcat所在目录
    public String config_file_dir; //properties 配置文件存放路径
    public String config_file_dir_xml; //xml配置文件路径
    //public String[] dependencyArtifactIds;//maven项目中依赖的jar包,注意依赖关系顺序。
    public boolean isBackups; //是否备份上个版本

    public static ServerEnumGit getServerEnumGit(ServerEnumGit serverEnum) {
        return serverEnum;
    }
}
