package com.example.authdemo.controller;

import com.example.authdemo.dto.ActorFilmCountDto;
import com.example.authdemo.dto.ActorAllCategoryDto;
import com.example.authdemo.dto.ActorRevenueDto;
import com.example.authdemo.dto.ActorRNotGDto;
import com.example.authdemo.dto.ActorCategoryAvgRentalDurationDto;
import com.example.authdemo.dto.ActorRLongNotGDto;
import com.example.authdemo.dto.ActorWithAllCoActorsDto;
import com.example.authdemo.dto.ActorNameDto;
import com.example.authdemo.entity.Actor;
import com.example.authdemo.service.ActorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    /**
     * Lấy danh sách tất cả diễn viên
     * Input: Không có (GET request)
     * Output: List<Actor> - Danh sách tất cả diễn viên với thông tin cơ bản
     */
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }
    
    @GetMapping("/more-than-20-films")
    /**
     * Lấy danh sách diễn viên đã tham gia hơn 20 bộ phim
     * Input: Không có (GET request)
     * Output: List<ActorFilmCountDto> - Danh sách diễn viên với tên và số lượng phim đã tham gia
     */
    public List<ActorFilmCountDto> getActorsWithMoreThan20Films() {
        return actorService.getActorsWithMoreThan20Films().stream()
                .map(a -> new ActorFilmCountDto(a.getFirstName(), a.getLastName(), a.getFilmCount()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/in-all-categories")
    /**
     * Lấy danh sách diễn viên đã tham gia phim thuộc tất cả các thể loại
     * Input: Không có (GET request)
     * Output: List<ActorAllCategoryDto> - Danh sách diễn viên đã tham gia đủ tất cả thể loại phim
     */
    public List<ActorAllCategoryDto> getActorsInAllCategories() {
        return actorService.getActorsInAllCategories().stream()
                .map(a -> new ActorAllCategoryDto(a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/total-revenue")
    /**
     * Lấy danh sách diễn viên và tổng doanh thu từ các phim họ tham gia
     * Input: Không có (GET request)
     * Output: List<ActorRevenueDto> - Danh sách diễn viên với tên và tổng doanh thu
     */
    public List<ActorRevenueDto> getActorsTotalRevenue() {
        return actorService.getActorsTotalRevenue().stream()
                .map(a -> new ActorRevenueDto(a.getFirstName(), a.getLastName(), a.getTotalRevenue()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/r-not-g")
    /**
     * Lấy danh sách diễn viên đã tham gia phim có rating R nhưng chưa tham gia phim rating G
     * Input: Không có (GET request)
     * Output: List<ActorRNotGDto> - Danh sách diễn viên chỉ tham gia phim R, không có phim G
     */
    public List<ActorRNotGDto> getActorsInRNotInG() {
        return actorService.getActorsInRNotInG().stream()
                .map(a -> new ActorRNotGDto(a.getFirstName(), a.getLastName()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/category-avg-rental-duration")
    /**
     * Lấy thời gian thuê trung bình của phim theo từng diễn viên và thể loại
     * Input: Không có (GET request)
     * Output: List<ActorCategoryAvgRentalDurationDto> - Thời gian thuê trung bình theo diễn viên và thể loại
     */
    public List<ActorCategoryAvgRentalDurationDto> getActorCategoryAvgRentalDuration() {
        return actorService.getActorCategoryAvgRentalDuration().stream()
                .map(a -> new ActorCategoryAvgRentalDurationDto(a.getActorId(), a.getActorName(), a.getCategoryId(), a.getCategoryName(), a.getAvgRentalDuration()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/r-long-not-g")
    /**
     * Lấy danh sách diễn viên đã tham gia phim R dài (trên 2 giờ) nhưng chưa tham gia phim G
     * Input: Không có (GET request)
     * Output: List<ActorRLongNotGDto> - Danh sách diễn viên tham gia phim R dài, không có phim G
     */
    public List<ActorRLongNotGDto> getActorsInRLongNotInG() {
        return actorService.getActorsInRLongNotInG().stream()
                .map(a -> new ActorRLongNotGDto(a.getActorName()))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/with-all-co-actors")
    /**
     * Lấy danh sách tất cả cặp diễn viên đã đóng chung phim và số lượng phim họ đóng chung
     * Input: Không có (GET request)
     * Output: List<ActorWithAllCoActorsDto> - Danh sách cặp diễn viên và số phim đóng chung
     */
    public List<ActorWithAllCoActorsDto> getActorsWithAllCoActors() {
        return actorService.getActorsWithAllCoActors().stream()
                .map(a -> new ActorWithAllCoActorsDto(a.getActorId(), a.getActorName(), a.getCoActorName(), a.getFilmsTogether()))
                .collect(Collectors.toList());
    }

    @GetMapping("/pg13-long-and-r-short")
    /**
     * Lấy danh sách diễn viên đã tham gia cả phim PG-13 dài (trên 2 giờ) và phim R ngắn (dưới 1 giờ)
     * Input: Không có (GET request)
     * Output: List<ActorNameDto> - Danh sách diễn viên thỏa mãn điều kiện
     */
    public List<ActorNameDto> getActorsInPg13LongAndRShortFilms() {
        return actorService.getActorsInPg13LongAndRShortFilms().stream()
                .map(a -> new ActorNameDto(a.getActorName()))
                .collect(Collectors.toList());
    }
}