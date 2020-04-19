package in.hocg.eagle.utils.web;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hocgin on 2020/4/19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
public class UserAgent {
    private final String userAgent;
    @ApiModelProperty("系统")
    private System system = System.Unknown;
    @ApiModelProperty("系统版本")
    private String systemVersion = "Unknown";
    @ApiModelProperty("平台")
    private Platform platform = Platform.Unknown;
    @ApiModelProperty("内核")
    private Engine engine = Engine.Unknown;
    @ApiModelProperty("内核版本")
    private String engineVersion = "Unknown";
    @ApiModelProperty("载体")
    private Supporter supporter = Supporter.Unknown;
    @ApiModelProperty("载体版本")
    private String supporterVersion = "Unknown";
    @ApiModelProperty("外壳")
    private Shell shell = Shell.Unknown;
    @ApiModelProperty("外壳版本")
    private String shellVersion = "Unknown";

    public UserAgent(String userAgent) {
        this.userAgent = userAgent.toLowerCase();
        // ==== 系统 ====
        if (testUa("windows|win32|win64|wow32|wow64")) {
            system = System.Windows;
        } else if (testUa("macintosh|macintel")) {
            system = System.MacOS;
        } else if (testUa("android|adr")) {
            system = System.Android;
        } else if (testUa("ios|iphone|ipad|ipod|iwatch")) {
            system = System.IOS;
        } else if (testUa("x11")) {
            system = System.Linux;
        }

        // ==== 系统版本 ====
        if (System.Windows.equals(system)) {
            if (testUa("windows nt 5.0|windows 2000")) {
                systemVersion = "2000";
            } else if (testUa("windows nt 5.1|windows xp")) {
                systemVersion = "xp";
            } else if (testUa("windows nt 5.2|windows 2003")) {
                systemVersion = "2003";
            } else if (testUa("windows nt 6.0|windows vista")) {
                systemVersion = "vista";
            } else if (testUa("windows nt 6.1|windows 7")) {
                systemVersion = "7";
            } else if (testUa("windows nt 6.2|windows 8")) {
                systemVersion = "8";
            } else if (testUa("windows nt 6.3|windows 8.1")) {
                systemVersion = "8.1";
            } else if (testUa("windows nt 10.0|windows 10")) {
                systemVersion = "10";
            }
        } else if (System.MacOS.equals(system)) {
            systemVersion = testVs("os x [\\d._]+");
        } else if (System.Android.equals(system)) {
            systemVersion = testVs("android [\\d._]+");
        } else if (System.IOS.equals(system)) {
            systemVersion = testVs("os [\\d._]+");
        }

        // ==== 平台 ====
        if (Lists.newArrayList(System.Windows, System.MacOS, System.Linux).contains(system)) {
            platform = Platform.Desktop;
        } else if (Lists.newArrayList(System.Android, System.IOS).contains(system) || testUa("mobile")) {
            platform = Platform.Mobile;
        }

        // ==== 内核和载体 ====
        if (testUa("applewebkit")) {
            engine = Engine.Webkit; // webkit内核
            if (testUa("edge")) {
                supporter = Supporter.Edge; // edge浏览器
            } else if (testUa("opr")) {
                supporter = Supporter.Opera; // opera浏览器
            } else if (testUa("chrome")) {
                supporter = Supporter.Chrome; // chrome浏览器
            } else if (testUa("safari")) {
                supporter = Supporter.Safari; // safari浏览器
            }
        } else if (testUa("gecko") && testUa("firefox")) {
            engine = Engine.Gecko; // gecko内核
            supporter = Supporter.Firefox; // firefox浏览器
        } else if (testUa("presto")) {
            engine = Engine.Presto; // presto内核
            supporter = Supporter.Opera; // opera浏览器
        } else if (testUa("trident|compatible|msie")) {
            engine = Engine.Trident; // trident内核
            supporter = Supporter.IExplore; // iexplore浏览器
        }

        // ==== 内核版本 ====
        if (Engine.Webkit.equals(engine)) {
            engineVersion = testVs("applewebkit\\/[\\d._]+");
        } else if (Engine.Gecko.equals(engine)) {
            engineVersion = testVs("gecko\\/[\\d._]+");
        } else if (Engine.Presto.equals(engine)) {
            engineVersion = testVs("presto\\/[\\d._]+");
        } else if (Engine.Trident.equals(engine)) {
            engineVersion = testVs("trident\\/[\\d._]+");
        }

        // ==== 载体版本 ====
        if (Supporter.Chrome.equals(supporter)) {
            supporterVersion = testVs("chrome\\/[\\d._]+");
        } else if (Supporter.Safari.equals(supporter)) {
            supporterVersion = testVs("version\\/[\\d._]+");
        } else if (Supporter.Firefox.equals(supporter)) {
            supporterVersion = testVs("firefox\\/[\\d._]+");
        } else if (Supporter.Opera.equals(supporter)) {
            supporterVersion = testVs("opr\\/[\\d._]+");
        } else if (Supporter.IExplore.equals(supporter)) {
            supporterVersion = testVs("(msie [\\d._]+)|(rv:[\\d._]+)");
        } else if (Supporter.Edge.equals(supporter)) {
            supporterVersion = testVs("edge\\/[\\d._]+");
        }

        // 外壳和外壳版本
        if (testUa("micromessenger")) {
            shell = Shell.WeChat;
            shellVersion = testVs("micromessenger\\/[\\d._]+");
        } else if (testUa("qqbrowser")) {
            shell = Shell.QQ;
            shellVersion = testVs("qqbrowser\\/[\\d._]+");
        } else if (testUa("ucbrowser")) {
            shell = Shell.UC;
            shellVersion = testVs("ucbrowser\\/[\\d._]+");
        } else if (testUa("qihu 360se")) {
            shell = Shell._360;
        } else if (testUa("2345explorer")) {
            shell = Shell._2345;
            shellVersion = testVs("2345explorer\\/[\\d._]+");
        } else if (testUa("metasr")) {
            shell = Shell.SouGou;
        } else if (testUa("lbbrowser")) {
            shell = Shell.LieBao;
        } else if (testUa("maxthon")) {
            shell = Shell.Maxthon;
            shellVersion = testVs("maxthon\\/[\\d._]+");
        }
    }

    private boolean testUa(String regex) {
        return Pattern.compile(regex).matcher(userAgent).find();
    }

    private String testVs(String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(userAgent);
        String result = "";
        if (matcher.find()) {
            result = matcher.group();
        }
        return result.replaceAll("[^0-9|_.]", "")
            .replaceAll("_", ".");
    }

    @Getter
    @RequiredArgsConstructor
    enum Platform {
        Desktop("Desktop"),
        Mobile("Desktop"),
        Unknown("Unknown");
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    enum System {
        Windows("Windows"),
        MacOS("MacOS"),
        Android("Android"),
        IOS("IOS"),
        Linux("Linux"),
        Unknown("Unknown");
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    enum Engine {
        Webkit("Webkit"),
        Gecko("Gecko"),
        Presto("Presto"),
        Trident("Trident"),
        Unknown("Unknown");
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    enum Supporter {
        Edge("Edge"),
        Opera("Opera"),
        Chrome("Chrome"),
        Safari("Safari"),
        Firefox("Firefox"),
        IExplore("IExplore"),
        Unknown("Unknown");
        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    enum Shell {
        WeChat("360浏览器"),
        UC("360浏览器"),
        _360("360浏览器"),
        _2345("搜狗浏览器"),
        SouGou("搜狗浏览器"),
        LieBao("猎豹浏览器"),
        Maxthon("遨游浏览器"),
        QQ("360浏览器"),
        Unknown("Unknown");
        private final String name;
    }

}
