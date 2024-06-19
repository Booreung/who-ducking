package hello.controller;

import hello.dto.admin.*;
import hello.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static hello.entity.request.RequestStatus.RECEIVED;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String adminPage() {
        return "admin/adminPage";
    }

    @GetMapping("/user-info")
    public String userInfoPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<UserInfoDTO> userPage = adminService.getUserInfoPage(page, 10);
        model.addAttribute("userPage", userPage);
        return "admin/userInfo";
    }

    @GetMapping("/add-animation")
    public String addAniPage() {
        return "admin/addAnimation";
    }

    @PostMapping("/add-animation")
    public String addAnimation(@ModelAttribute AnimationDTO animationDTO) throws IOException {
        adminService.saveAnimation(animationDTO);
        return "redirect:/admin";
    }

    @GetMapping("/request-list")
    public String requestList(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<RequestListDTO> requestPage = adminService.getRequestsByStatus(RECEIVED, page, 10);
        model.addAttribute("requestPage", requestPage);
        return "admin/requestList";
    }

    @GetMapping("/request/{id}")
    public String viewRequest(@PathVariable(name = "id") Long id, Model model) {
        RequestDetailDTO requestDetail = adminService.getRequestById(id);
        model.addAttribute("requestDetail", requestDetail);
        return "admin/requestDetail";
    }

    @PostMapping("/request/{id}/approve")
    public String approveRequest(@PathVariable(name = "id") Long id,
                                 @ModelAttribute AnimationDTO animationDTO) throws IOException {
        adminService.approveRequest(id);
        adminService.saveAnimation(animationDTO);
        return "redirect:/admin/request-list";
    }

    @PostMapping("/request/{id}/reject")
    public String rejectRequest(@PathVariable(name = "id") Long id,
                                @ModelAttribute RequestRejectDTO requestRejectDTO) {
        requestRejectDTO.setId(id);
        adminService.rejectRequest(requestRejectDTO);
        return "redirect:/admin/request-list";
    }

    @GetMapping("/prize")
    public String prizePage(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<PrizeListDTO> currentPrizes = adminService.getCurrentPrizes(page, 10);
        Page<PrizeListDTO> expiredPrizes = adminService.getExpiredPrizes(page, 10);
        model.addAttribute("currentPrizes", currentPrizes);
        model.addAttribute("expiredPrizes", expiredPrizes);
        return "admin/prize";
    }

    @PostMapping("/add-prize")
    public String addPrize(@ModelAttribute PrizeAddDTO prizeAddDTO) throws IOException {
        adminService.addPrize(prizeAddDTO);
        return "redirect:/admin/prize";
    }

    @GetMapping("/announcement")
    public String announcementPage(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<AnnouncementListDTO> announcementPage = adminService.getAnnouncementPage(page, 5);
        model.addAttribute("announcements", announcementPage);
        return "admin/announcement";
    }

    @PostMapping("/announcement")
    public String writeAnnouncement(@ModelAttribute AnnouncementWriteDTO announcementWriteDTO) {
        adminService.addAnnouncement(announcementWriteDTO);
        return "redirect:/admin/announcement";
    }

    @PostMapping("/announcement-delete")
    public String deleteAnnouncement(@RequestParam("id") Long id) {
        adminService.deleteAnnouncement(id);
        return "redirect:/admin/announcement";
    }
}