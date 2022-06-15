package zust.competition.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zust.competition.sys.dto.CompetitionDto;
import zust.competition.sys.dto.UserDto;
import zust.competition.sys.entity.Competition;
import zust.competition.sys.entity.Query;
import zust.competition.sys.service.CompetitionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/competition")
public class AdminController {

    @Autowired
    private CompetitionService competitionService;
    @Value("${upload.location.pics}")
    private String uploadPicsPath;
    @Value("${upload.location.files}")
    private String uploadFilesPath;
    @Value("${upload.route.pics}")
    private String uploadPicsRoute;
    @Value("${upload.route.files}")
    private String uploadFilesRoute;
    @Value("${gateway.route.name}")
    private String gatewayRoute;

    @GetMapping("/post/info")
    public String toPostInfo() {
        return "admin/postCompetition";
    }

    /**
     * 发布竞赛信息
     */
    @PostMapping("/post/info")
    public String postInfo(Competition competition, Model model, HttpSession session){
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        competition.setOperatorId(userId);
        Integer id = competitionService.addCompetition(competition);
        if (id == -1) {
            model.addAttribute("msg","发布失败");
            return "admin/postCompetition";
        }
        model.addAttribute("cpId",id);
        model.addAttribute("msg1","发布成功");
        return "admin/postCompetition";
    }

    /**
     * 上传竞赛图片
     */
    @PostMapping("/post/pic")
    public String postPicture(HttpServletRequest request, Model model, HttpSession session) {
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        File filePath = new File(uploadPicsPath);
        if(!filePath.exists())  filePath.mkdirs();
        //页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("pic");
        Integer cpId = 0;
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().equals("")) {
            //生成文件名称
            String logImageName = (new Date()).getTime() + multipartFile.getOriginalFilename();
            //拼成完整的文件保存路径加文件
            String filename = uploadPicsPath + File.separator + logImageName;
            File file = new File(filename);
            try {
                multipartFile.transferTo(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 将图片信息保存到数据库
            CompetitionDto dto = new CompetitionDto();
            cpId = Integer.parseInt(request.getParameter("cpId"));
            dto.setId(cpId);
            dto.setPicture(gatewayRoute + uploadPicsRoute.replace("*","") + logImageName);
            dto.setOperatorId(userId);
            competitionService.updateCompetition(dto);
            model.addAttribute("cpId",cpId);
            model.addAttribute("msg2", "图片上传成功");
        }
        return toUpdateCompetition(cpId, model);
    }

    /**
     * 上传竞赛附件
     */
    @PostMapping("/post/supplement")
    public String postSupplement(HttpServletRequest request, Model model, HttpSession session) {
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        File fileDir = new File(uploadFilesPath);
        if (!fileDir.exists()) fileDir.mkdirs();
        //页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("supplement");
        Integer cpId = 0;
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().equals("")) {
            //生成文件名称
            String oldFileName = multipartFile.getOriginalFilename();
            String newFileName = (new Date()).getTime() + oldFileName;
            //拼成完整的文件保存路径加文件
            String firePath = uploadFilesPath + File.separator + newFileName;
            File file = new File(firePath);
            try {
                multipartFile.transferTo(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 将文件信息保存到数据库
            CompetitionDto dto = new CompetitionDto();
            cpId = Integer.parseInt(request.getParameter("cpId"));
            dto.setId(cpId);
            dto.setSupplement(oldFileName);
            dto.setSupplementPath(newFileName);
            dto.setOperatorId(userId);
            competitionService.updateCompetition(dto);
            model.addAttribute("cpId", cpId);
            model.addAttribute("msg3", "文件上传成功");
        }
        return toUpdateCompetition(cpId, model);
    }

    /**
     * 竞赛信息管理
     */
    @GetMapping("/list")
    public String toCompetitionList(Model model) {
        List<CompetitionDto> competitionList = competitionService.getCompetitionList();
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    /**
     * 搜索竞赛信息
     * 模糊查询：标题、内容、组织机构
     */
    @PostMapping("/search")
    public String searchCompetition(Query query, Model model) {
        List<CompetitionDto> competitionList = competitionService.searchCompetition(query);
        model.addAttribute("competitionList", competitionList);
        return "admin/competitionList";
    }

    /**
     * 删除一条竞赛信息
     */
    @GetMapping("/delete/{id}")
    public String deleteCompetition(@PathVariable("id") Integer id, Model model){
        if(-1 == competitionService.deleteCompetition(id)){
            model.addAttribute("msg", "该竞赛下已有报名团队，不能删除!");
            return toCompetitionList(model);
        }
        model.addAttribute("msg", "删除成功!");
        return toCompetitionList(model);
    }

    /**
     * 编辑竞赛信息页面
     */
    @GetMapping("/update/{id}")
    public String toUpdateCompetition(@PathVariable("id") Integer id,Model model){
        CompetitionDto competition = competitionService.detail(id);
        model.addAttribute("data",competition);
        model.addAttribute("cpId",id);
        return "admin/updateCompetition";
    }

    /**
     * 编辑竞赛信息
     */
    @PostMapping("/update")
    public String updateCompetition(CompetitionDto dto, Model model, HttpSession session) {
        Integer userId = ((UserDto) session.getAttribute("thisUser")).getId();
        dto.setOperatorId(userId);
        if(competitionService.updateCompetition(dto) == -1) {
            model.addAttribute("msg1", "修改竞赛信息失败!");
            return toUpdateCompetition(dto.getId(),model);
        }
        model.addAttribute("msg1", "修改竞赛信息成功!");
        return toUpdateCompetition(dto.getId(),model);
    }

}
