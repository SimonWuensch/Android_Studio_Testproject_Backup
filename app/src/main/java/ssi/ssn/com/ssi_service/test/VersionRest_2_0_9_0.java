package ssi.ssn.com.ssi_service.test;

public class VersionRest_2_0_9_0 extends TestRestResponse {

    private static String LOGIN = "{\"id\":2824521,\"key\":\"inLlrYu9c6q1mBySFOA2VTX_V8A-CQFjy95zP6SM\",\"status\":\"LOGGED_IN\",\"actor\":{\"key\":\"admin\"},\"rights\":[\"scada-write\",\"component-write\",\"notification-write\",\"security-read\",\"application-read\",\"smt-read\",\"security-write\",\"kpi-read\",\"notification-read\",\"kpi-write\",\"component-read\",\"scada-read\"],\"createdOn\":1483533985642,\"lastModifiedOn\":1483533985715,\"clientAddress\":\"10.245.0.94\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\"}";
    private static String LOGOUT = "{\"id\":2824735,\"key\":\"h2tYOymJeFEqTivDWcvsYL_5sdpkaKl2EJYRZfkg\",\"status\":\"LOGGED_OUT\",\"createdOn\":1483534612067,\"lastModifiedOn\":1483535346013,\"clientAddress\":\"10.245.0.94\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\"}";
    private static String APPLICATION = "{\"state\":{\"since\":1482487804669,\"status\":\"RUNNING\"},\"enabledModules\":[\"Scada\",\"Kpi.UserCharts\",\"Kpi.UserDashboards\"],\"project\":{\"name\":\"TZ\",\"location\":\"Giebelstadt\",\"orderNr\":\"2x0\"},\"build\":{\"version\":\"2.0.9.0\",\"number\":\"8439\",\"builtBy\":\"WU-LIGHTHOUSE$\",\"builtOn\":1482487531909},\"time\":{\"stamp\":1483533997191,\"offset\":3600000}}";
    private static String APPLICATION_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><runtime-config runtime-type=\"TEST\" version=\"2.0\" license-key=\"5061f7ca2c1e8bfa6a04da499974aecc30323127\"><product><platform-modules><application-module suppress-threshold=\"0\" stop-on-error=\"false\"/><security-module><sessions timeout=\"180\" floating-max-active=\"20\" validation-period=\"1800\" remove-after=\"172800\"/></security-module><notification-module remove-after=\"12\"/><kpi-module user-charts-enabled=\"true\" user-dashboards-enabled=\"true\" remove-after=\"12\"/><components-module><app-server service-name=\"LIGHTHOUSE\"><connection address=\"http://localhost:10090\"/><application-connection address=\"http://localhost:8180\"/></app-server><license-server manage=\"true\" start-timeout=\"5000\" stop-timeout=\"5000\" overwatch=\"60\"><connection address=\"http://localhost:7100\"/></license-server><opcua-server initialize-client=\"true\" manage=\"true\" start-timeout=\"15000\" stop-timeout=\"10000\" overwatch=\"60\"><connection address=\"opc.tcp://localhost:7110\"/></opcua-server><webmi-server enabled=\"false\" manage=\"false\" start-timeout=\"10000\" stop-timeout=\"10000\" overwatch=\"60\"><connection address=\"http://localhost:7120\"/></webmi-server><database><connection address=\"jdbc:sqlserver://localhost:1433;databaseName=LIGHTHOUSE\" user=\"lighthouse\" password=\"lighthouse\"/></database></components-module></platform-modules><plugin-modules><scada-module enabled=\"true\" auto-start=\"true\"/><smt-module enabled=\"false\" auto-start=\"false\"><smt-adapter address=\"http://localhost:8081/smt-adapter\" user=\"lh\" password=\"lh\"/></smt-module></plugin-modules></product><project name=\"TZ\" order-nr=\"2x0\" location=\"Giebelstadt\"><customer name=\"TZ\" address=\"Giebelstadt\"/></project></runtime-config>";
    private static String COMPONENT_LICENSE = "{\"name\":\"license\",\"config\":{\"home\":\"${RUNTIME_HOME}/components/license\",\"address\":\"http://localhost:7100\",\"manage\":true,\"startTimeout\":5000,\"stopTimeout\":5000,\"overwatch\":60},\"state\":{\"name\":\"License\",\"managed\":true,\"status\":\"ONLINE\",\"since\":1483534924779}}";
    private static String COMPONENT_OPCUA = "{\"name\":\"OpcUa\",\"config\":{\"home\":\"${RUNTIME_HOME}/components/opcua\",\"address\":\"opc.tcp://localhost:6110\",\"manage\":true,\"startTimeout\":5000,\"stopTimeout\":5000,\"overwatch\":60},\"state\":{\"name\":\"OpcUa\",\"managed\":true,\"status\":\"ONLINE\",\"since\":1489148813550}}";
    private static String COMPONENT_WEBMI = "{\"name\":\"webmi\",\"config\":{\"home\":\"${RUNTIME_HOME}/components/webmi\",\"address\":\"http://localhost:7120\",\"manage\":false,\"startTimeout\":10000,\"stopTimeout\":10000,\"overwatch\":60},\"state\":{\"name\":\"WebMi\",\"managed\":false,\"status\":\"UNKNOWN\",\"since\":1482487800032}}";
    private static String MODULE_APPLICATION_STATE = "{\"name\":\"Application\",\"status\":\"RUNNING\",\"since\":1482487804669}";
    private static String MODULE_SECURITY_STATE = "{\"name\":\"Security\",\"status\":\"RUNNING\",\"since\":1482487804568}";
    private static String MODULE_COMPONENT_STATE = "{\"name\":\"Component\",\"status\":\"RUNNING\",\"since\":1482487804560}";
    //private static String MODULE_COMPONENT_STATE = "{\"name\":\"Component\",\"status\":\"NOT_RUNNING\",\"since\":1482487804560}";
    private static String MODULE_NOTIFICATION_STATE = "{\"name\":\"Notification\",\"status\":\"RUNNING\",\"since\":1482487804601}";
    private static String MODULE_KPI_STATE = "{\"name\":\"Kpi\",\"status\":\"RUNNING\",\"since\":1482487804584}";
    private static String MODULE_SCADA_STATE = "{\"name\":\"Scada\",\"status\":\"RUNNING\",\"since\":1482487804668}";
    private static String MODULE_SMT_STATE = "{\"name\":\"SMT\",\"status\":\"RUNNING\",\"since\":1482487804668}";
    private static String OS = "{\"name\":\"Windows Server 2012 R2\",\"version\":\"6.3\",\"arch\":\"amd64\",\"processors\":4,\"avgSystemLoad\":-1.0,\"processCpuLoad\":0.0004208007886676659,\"processCpuLoadLast\":0.000455733278140391,\"systemCpuLoad\":0.06487968004977607,\"systemCpuLoadLast\":0.0118079273392101,\"diskFreeSpace\":5441,\"jvmImplSpecifics\":{\"jvmImplClassName\":\"sun.management.OperatingSystemImpl\",\"CommittedVirtualMemorySize\":\"2895667200\",\"CommittedVirtualMemorySize0\":\"2895667200\",\"FreePhysicalMemorySize\":\"20454989824\",\"FreeSwapSpaceSize\":\"20513234944\",\"ProcessCpuLoad\":\"4.208007886676659E-4\",\"ProcessCpuTime\":\"1614250000000\",\"SystemCpuLoad\":\"0.06487968004977607\",\"TotalPhysicalMemorySize\":\"34359267328\",\"TotalSwapSpaceSize\":\"39459540992\"},\"disk\":{\"usable\":5441,\"total\":81567,\"fillPercent\":\"93.33\"}}";
    private static String SCADA_SYSTEMS = "[{\"name\":\"C01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.28\",\"nodePath\":\"PLANT.PHYSICAL.CS.C01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483514253793,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"F01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.25\",\"nodePath\":\"PLANT.PHYSICAL.CS.F01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"R01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.21\",\"nodePath\":\"PLANT.PHYSICAL.SRM.R01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"R42\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.31\",\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"STS11\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.13\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS11\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"STS12\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.16\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS12\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"STS21\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.44\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS21\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706122,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"TL01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.10\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483180706123,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"TL20\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.40\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1483481116373,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"},{\"name\":\"W111\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.26\",\"nodePath\":\"PLANT.PHYSICAL.Wrapper.W111\",\"type\":\"S7\",\"integrationType\":\"S7\",\"connectionStatus\":\"CONNECTION_ERROR\",\"connectionStatusTimestamp\":1482487814677,\"connectionActive\":false,\"status\":\"NOT_AVAILABLE\"}]";
    private static String SCADA_SCANNERS = "[]";
    private static String SESSIONSCURRENT_LOGGED_IN = "{\"id\":2824735,\"key\":\"h2tYOymJeFEqTivDWcvsYL_5sdpkaKl2EJYRZfkg\",\"status\":\"LOGGED_IN\",\"actor\":{\"key\":\"admin\"},\"rights\":[\"scada-write\",\"component-write\",\"notification-write\",\"security-read\",\"application-read\",\"smt-read\",\"security-write\",\"kpi-read\",\"notification-read\",\"kpi-write\",\"component-read\",\"scada-read\"],\"createdOn\":1483534612067,\"lastModifiedOn\":1483534921349,\"clientAddress\":\"10.245.0.94\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\"}";
    private static String SESSIONSCURRENT_LOGGED_OUT = "{\"id\":2824735,\"key\":\"h2tYOymJeFEqTivDWcvsYL_5sdpkaKl2EJYRZfkg\",\"status\":\"LOGGED_OUT\",\"createdOn\":1483534612067,\"lastModifiedOn\":1483535346013,\"clientAddress\":\"10.245.0.94\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36\"}";
    private static String NOTIFICATION = "{\"count\":17,\"data\":[{\"id\":2822618,\"startTime\":1483524998645,\"text\":\"C01WC1100 message window conveyer is empty\",\"nodePath\":\"PLANT.PHYSICAL.CS.C01.C0100.C0100XXX201\",\"tKey\":\"PLANT.NS.TRIGGERS.C01\",\"tIdx\":619,\"definition\":{\"key\":\"6eaa193a4f897e099be820fbf3a11ddae52c2d58\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3949}},{\"id\":2822617,\"startTime\":1483524980622,\"text\":\"External control not in automatic\",\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0106.F0106TSLC072\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":5290,\"definition\":{\"key\":\"1dcbfaa3fda307e8c9afaa38a9a75d8e226b2fc8\",\"type\":\"SCADA\",\"severity\":\"WARN\",\"docNr\":1867}},{\"id\":2822590,\"startTime\":1483524859489,\"text\":\"Warning Waiting on MFS-order\",\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0104.F0104CC032\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":2358,\"definition\":{\"key\":\"94f0ec3525c82c6537b8e510ff719d2e42fe2cec\",\"type\":\"SCADA\",\"severity\":\"WARN\",\"docNr\":1831}},{\"id\":2822589,\"startTime\":1483524857493,\"text\":\"TL20 +P01 message wait for order\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL2000.TL2000XXX011\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":175,\"definition\":{\"key\":\"405f2c146930684fdbb41252269359f046511409\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3980}},{\"id\":2821696,\"startTime\":1483520788139,\"text\":\"Testmode activated (143.0)(51;2;2;0)\",\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"tKey\":\"PLANT.NS.TRIGGERS.R42\",\"tIdx\":1064,\"definition\":{\"key\":\"6a9efb99dede1fa94a20f2f6d0bc647d3cdd2dab\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3065}},{\"id\":2602372,\"startTime\":1482488100512,\"text\":\"Connection lost\",\"nodePath\":\"PLANT.PHYSICAL.Wrapper.W111\",\"definition\":{\"key\":\"a3247a110ded748649648afe98491faed5d547e5\",\"type\":\"SCADA\",\"severity\":\"WARN\"}},{\"id\":2602305,\"startTime\":1482487806705,\"text\":\"TL2013LSD003 maintenance interval expired\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":64,\"definition\":{\"key\":\"1ba4471acd4c78caa638b6f34a488e040dd9b60c\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3618}},{\"id\":2602307,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 23 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":546,\"definition\":{\"key\":\"5ba33eb0fe20f09a749fc4b55a6069fc34a75e56\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3961}},{\"id\":2602308,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 33 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":642,\"definition\":{\"key\":\"905a44eb7408190d964e62254988cc0e36c5822a\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3961}},{\"id\":2602309,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 41 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":674,\"definition\":{\"key\":\"7a4d0fefb97d0390110afaa2ac4bebe4c101d00c\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":2602310,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 43 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":738,\"definition\":{\"key\":\"12d08a1f81b43b9672439a931d1186a441a82787\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3961}},{\"id\":2602311,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 52 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":802,\"definition\":{\"key\":\"7bf48058bc31e4cc26e034afa18a18d8d4ddb3eb\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":2602312,\"startTime\":1482487806705,\"text\":\"TL2014 Node-ID 63 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":930,\"definition\":{\"key\":\"e729610942f12fe9a8386a0ad09a7310972a7165\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":2602303,\"startTime\":1482487806696,\"text\":\"TL0113LAM003 maintenance interval expired\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL01.TL01Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL01\",\"tIdx\":64,\"definition\":{\"key\":\"c60ca3a6edeec5f9edc919550108bff0ff1a4aac\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3618}},{\"id\":2602304,\"startTime\":1482487806696,\"text\":\"TL0114 Node-ID 21 message one or some motors off\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL01.TL01Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL01\",\"tIdx\":482,\"definition\":{\"key\":\"00cb229ef7f107a4b57617201bfa082526c1884b\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":3961}},{\"id\":2602300,\"startTime\":1482487806647,\"text\":\"Warning Waiting on MFS-order\",\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0105.F0105LRB047\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":3606,\"definition\":{\"key\":\"94f0ec3525c82c6537b8e510ff719d2e42fe2cec\",\"type\":\"SCADA\",\"severity\":\"WARN\",\"docNr\":1831}},{\"id\":2602298,\"startTime\":1482487804676,\"text\":\"APP-INFO: Application Started\",\"definition\":{\"key\":\"5e07799b997382e6a55da379bed44f07e9898d90\",\"type\":\"APPLICATION\",\"severity\":\"INFO\"}}]}";
    private static String NOTIFICATION_COUNT = "17";
    private static String NOTIFICATION_FILTER_TL20Lift = "{ \"count\":1, \"data\":[ { \"id\":2602308, \"startTime\":1488538200000, \"text\":\"TL2014 Node-ID 33 message one or some motors off\", \"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\", \"tKey\":\"PLANT.NS.TRIGGERS.TL20\", \"tIdx\":642, \"definition\":{ \"key\":\"905a44eb7408190d964e62254988cc0e36c5822a\", \"type\":\"SCADA\", \"severity\":\"ERROR\", \"docNr\":3961 } } ] }";
    private static String NOTIFICATION_FILTER_W111 = "{ \"count\":1, \"data\":[ { \"id\":2602372, \"startTime\":1482488100512, \"text\":\"Connection lost\", \"nodePath\":\"PLANT.PHYSICAL.Wrapper.W111\", \"definition\":{ \"key\":\"a3247a110ded748649648afe98491faed5d547e5\", \"type\":\"SCADA\", \"severity\":\"WARN\" } } ] }";
    private static String NOTIFICATION_FILTER_TEST = "{ \"count\":0, \"data\":[ ] }";
    private static String NOTIFICATION_FILTER_NULL = "{ \"count\":0, \"data\":[ ] }";
    private static String KPI_DEFINITIONS = "[{\"key\":\"lh.application.CpuProcessLoad\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Lighthouse Process CPU Usage\",\"units\":\"%\",\"description\":\"CPU usage of Lighthouse\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.application.CpuSystemLoad\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"System CPU Usage\",\"units\":\"%\",\"description\":\"Overall CPU usage of system\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.application.DiskFreeSpace\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"MIN\",\"location\":\"Lighthouse\",\"name\":\"Lighthouse File System Free Space\",\"units\":\"MB\",\"description\":\"Amount of space left in File System (Disk)\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.notification.Active\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Active Notifications\",\"units\":\"Count\",\"description\":\"Number of Active Notifications by Type\",\"spectrumCategories\":[\"ERROR\",\"WARN\",\"INFO\"],\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.notification.Activated\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Activated Notifications\",\"units\":\"Count\",\"description\":\"Number of Notifications Activated\",\"spectrumCategories\":[\"ERROR\",\"WARN\",\"INFO\"],\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.security.ActiveSessions\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"MAX\",\"location\":\"Lighthouse\",\"name\":\"Active Sessions\",\"units\":\"Sessions\",\"description\":\"Number of Active Sessions\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.Updates\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Trigger Updates\",\"units\":\"Updates\",\"description\":\"Number of OPC-UA trigger updates\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.ProcessingTime\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Trigger Processing Time\",\"units\":\"MS/Update\",\"description\":\"Amount of time to process the trigger updates\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.LostNotifications\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Lost Notifications\",\"units\":\"Notifications\",\"description\":\"Number of Lost Notifications\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Sent\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Sent\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams sent\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Received\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Received\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams received\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Ignored\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Ignored\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams ignored\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Picking.Station1.Occupied\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0204CC037\",\"name\":\"Pallet Picking Time Station 1\",\"units\":\"MS\",\"description\":\"Length of Picking Time in Station 1\",\"spectrumCategories\":[\"Occupied\",\"Unoccupied\"],\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Receiving.Accepted\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC011\",\"name\":\"Received Pallets\",\"units\":\"Pallets\",\"description\":\"Number of pallets received\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Receiving.Occupied\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC001\",\"name\":\"Receiving Occupied Status\",\"units\":\"MS\",\"description\":\"Occupied status of Receiving\",\"spectrumCategories\":[\"Occupied\",\"Unoccupied\"],\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Receiving.Rejected\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC010\",\"name\":\"Rejected Pallets\",\"units\":\"Pallets\",\"description\":\"Number of pallets rejected\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane1\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0106LCC052\",\"name\":\"Lane 1 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 1\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane2\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0106LCC057\",\"name\":\"Lane 2 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 2\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane3\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0107LCC062\",\"name\":\"Lane 3 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 3\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane4\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0107LCC067\",\"name\":\"Lane 4 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 4\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane5\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0108LCC072\",\"name\":\"Lane 5 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 5\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Shipping.Lane6\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0108LCC077\",\"name\":\"Lane 5 Pallets Shipped\",\"units\":\"Pallets\",\"description\":\"Number of pallets shipped in Lane 6\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Storage.RH101.Inbound\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC021\",\"name\":\"Pallets stored in RH101\",\"units\":\"Pallets\",\"description\":\"Number of pallets stored in RH101\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Storage.RH101.Outbound\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC022\",\"name\":\"Pallets retrieved from RH101\",\"units\":\"Pallets\",\"description\":\"Number of pallets received from RH101\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Storage.RH102.Inbound\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0103CC029\",\"name\":\"Pallets stored in RH102\",\"units\":\"Pallets\",\"description\":\"Number of pallets stored in RH102\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"PLC.Storage.RH102.Outbound\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0104CC030\",\"name\":\"Pallets retrieved from RH102\",\"units\":\"Pallets\",\"description\":\"Number of pallets received from RH102\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"SCRIPTED.lh.test.Random\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_DOUBLE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"\",\"name\":\"Scripted Random\",\"units\":\"\",\"description\":\"Random scripted value\",\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"sap.test\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"SAP Test KPI\",\"units\":\"%\",\"description\":\"SAP Test KPI for Lighthouse\",\"removeAfter\":6},{\"key\":\"sap.test2\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"SAP Test 2 KPI\",\"units\":\"%\",\"description\":\"SAP Test 2 KPI for Lighthouse\",\"removeAfter\":10},{\"key\":\"sap.test3\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"SAP Test 2 KPI\",\"units\":\"%\",\"description\":\"SAP Test 2 KPI for Lighthouse\",\"removeAfter\":6},{\"key\":\"sap.ordim_o\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"SAP ORDIM_O\",\"description\":\"SAP EWM open WH-Tasks\",\"removeAfter\":10},{\"key\":\"sap.ordim_o.2\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"SAP ORDIM_O\",\"description\":\"SAP EWM open WH-Tasks\",\"removeAfter\":10},{\"key\":\"sap.ordim\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"SAP ORDIM\",\"description\":\"SAP EWM WH-Tasks\",\"spectrumCategories\":[\"Open\",\"Blocked\"],\"removeAfter\":10},{\"key\":\"KPI.Test.PalletsReceived\",\"provider\":\"MFS\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0102CC011\",\"name\":\"TEST Received Pallets\",\"units\":\"Pallets\",\"description\":\"Number of Pallets Received\",\"evaluationScript\":\"\",\"removeAfter\":1},{\"key\":\"KPI.Test.PickingTime.Position1\",\"provider\":\"MFS\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"F0203CC020\",\"name\":\"TEST Picking Time Position 1\",\"units\":\"Picking Time\",\"description\":\"Average Time for Picking\",\"evaluationScript\":\"\",\"removeAfter\":1},{\"key\":\"sppg.aspect1.value1\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"DAY\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"\"},{\"key\":\"SPPG.load.loadindex.LocalDispatcher1\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"The load of LocalDispatcher1\"},{\"key\":\"SPPG.load.loadindex.LocalDispatcher2\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"The load of LocalDispatcher2\"},{\"key\":\"SPPG.kpi.NumberOfPallets\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"DAY\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"Number of Pallets in Number of Pallets\"},{\"key\":\"SPPG.kpi.NumberOfOrders\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"DAY\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"Number of Orders in Number of Orders\"},{\"key\":\"SPPG.kpi.AdditionalPalletDemand\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_DOUBLE\",\"minimalGranularity\":\"DAY\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"Additional Pallet Demand in %\"},{\"key\":\"SPPG.kpi.MeanTimePerPallet\",\"provider\":\"SPPG\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"DAY\",\"aggregationStrategy\":\"PROVIDER\",\"location\":\"SPPG_CLUSTER\",\"description\":\"Mean Time per Pallet in s\"},{\"key\":\"KPI.CS.F01.F0101CC001.Occupied\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0101CC001\",\"name\":\"Occupation of F0101CC001\",\"units\":\"MS\",\"description\":\"Occupation of F0101CC001\",\"spectrumCategories\":[\"Occupied\",\"Unoccupied\"],\"evaluationScript\":\"\",\"removeAfter\":6},{\"key\":\"KPI.SRM.R01.Status\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"R01\",\"name\":\"R01 Status\",\"units\":\"MS\",\"description\":\"Status of SRM R01\",\"spectrumCategories\":[\"Zero\",\"One\",\"Two\",\"Three\"],\"evaluationScript\":\"\",\"removeAfter\":6}]";
    private static String KPI_MESUREMENTS = "[{\"defKey\":\"lh.application.CpuProcessLoad\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0008463525529544657,\"avg\":0.0008463525529544657,\"numSamples\":1,\"min\":0.0008463525529544657,\"max\":0.0008463525529544657,\"stdDev\":0.0},{\"defKey\":\"lh.application.CpuSystemLoad\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.015822222638221417,\"avg\":0.015822222638221417,\"numSamples\":1,\"min\":0.015822222638221417,\"max\":0.015822222638221417,\"stdDev\":0.0},{\"defKey\":\"lh.application.DiskFreeSpace\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":4430},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":1.0,\"channelIndex\":0},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0,\"channelIndex\":1},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":1.0,\"channelIndex\":2},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0,\"channelIndex\":0},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0,\"channelIndex\":1},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0,\"channelIndex\":2},{\"defKey\":\"lh.security.ActiveSessions\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":10.5,\"avg\":10.5,\"numSamples\":2,\"min\":10.0,\"max\":11.0,\"stdDev\":0.5},{\"defKey\":\"lh.scada.trigger.Updates\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0},{\"defKey\":\"lh.scada.trigger.ProcessingTime\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0.0,\"avg\":0.0,\"numSamples\":0,\"min\":0.0,\"max\":0.0,\"stdDev\":0.0},{\"defKey\":\"lh.scada.trigger.LostNotifications\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491555960000,\"value\":0},{\"defKey\":\"sap.ordim_o\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1481105040000,\"value\":78},{\"defKey\":\"sap.ordim_o.2\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1476271260000,\"value\":76.0,\"avg\":76.0,\"numSamples\":1,\"min\":76.0,\"max\":76.0,\"stdDev\":0.0},{\"defKey\":\"sap.ordim\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1481105040000,\"value\":55.0,\"channelIndex\":0},{\"defKey\":\"sap.ordim\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1481105040000,\"value\":6.0,\"channelIndex\":1},{\"defKey\":\"SPPG.load.loadindex.LocalDispatcher1\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1479815340000,\"value\":0},{\"defKey\":\"SPPG.load.loadindex.LocalDispatcher2\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1479815340000,\"value\":0}]";
    // Response geholt von Server http://172.26.26.16:8180/ - "project":{"name":"TZ","location":"Giebelstadt","orderNr":"2x0"
    public String test;


    public VersionRest_2_0_9_0() {
        super("2.0.9.0");

        addResponse("/services/security/login/admin/admin", LOGIN);
        addResponse("/services/security/logout", LOGOUT);
        addResponse("/services/application", APPLICATION);
        addResponse("/services/application/config", APPLICATION_CONFIG);
        addResponse("/services/component/license", COMPONENT_LICENSE);
        addResponse("/services/component/opcua", COMPONENT_OPCUA);
        addResponse("/services/component/webmi", COMPONENT_WEBMI);
        addResponse("/services/application/state", MODULE_APPLICATION_STATE);
        addResponse("/services/security/state", MODULE_SECURITY_STATE);
        addResponse("/services/component/state", MODULE_COMPONENT_STATE);
        addResponse("/services/notification/state", MODULE_NOTIFICATION_STATE);
        addResponse("/services/kpi/state", MODULE_KPI_STATE);
        addResponse("/services/scada/state", MODULE_SCADA_STATE);
        addResponse("/services/smt/state", MODULE_SMT_STATE);
        addResponse("/services/application/diagnostics/os", OS);
        addResponse("/services/scada/systems", SCADA_SYSTEMS);
        addResponse("/services/scada/scanners", SCADA_SCANNERS);
        addResponse("/services/security/sessions/current", SESSIONSCURRENT_LOGGED_IN);

        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>>", NOTIFICATION);
        addResponse("/services/notification/count?orderBy=$<<startTime>> DESC&condition=$<<active>>", NOTIFICATION_COUNT);
        //.66.97:8080/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> LIKE '%25TL2014 Node-ID 33 message one or some motors off%25') AND ($<<nodePath>> IN ('TL20Lift')))
        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> LIKE '%25TL2014 Node-ID 33 message one or some motors off%25') AND ($<<nodePath>> IN ('TL20Lift')))", NOTIFICATION_FILTER_TL20Lift);
        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'WARN') AND ($<<text>> LIKE '%25Connection lost%25') AND ($<<nodePath>> IN ('W111')))", NOTIFICATION_FILTER_W111);
        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> LIKE '%25test%25') AND ($<<nodePath>> IN ('test')))", NOTIFICATION_FILTER_TEST);
        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> LIKE '%25%25') AND ($<<nodePath>> IN ('')))", NOTIFICATION_FILTER_NULL);

        addResponse("/services/kpi/currentDefinitions", KPI_DEFINITIONS);
        addResponse("/services/kpi/measurements", KPI_MESUREMENTS);
    }
}
