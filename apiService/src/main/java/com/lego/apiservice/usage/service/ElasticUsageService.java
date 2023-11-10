package com.lego.apiservice.usage.service;

import com.lego.apiservice.api.entity.domain.Api;
import com.lego.apiservice.api.entity.domain.ApiMethod;
import com.lego.apiservice.api.repostiory.ApiRepository;
import com.lego.apiservice.category.repository.CategoryRepository;
import com.lego.apiservice.usage.entity.domain.ElasticUsage;
import com.lego.apiservice.usage.entity.dto.request.CreateUsageRequest;
import com.lego.apiservice.usage.entity.dto.response.ElasticUsageResponse;
import com.lego.apiservice.usage.entity.dto.statistics.*;
import com.lego.apiservice.usage.repository.ElasticUsageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticUsageService {

    private final ElasticUsageRepository elasticUsageRepository;
    private final ApiRepository apiRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void register(CreateUsageRequest createUsageRequest) {
        ElasticUsage usage = ElasticUsage.builder()
                .createdAt(createUsageRequest.getCreatedAt())
                .method(createUsageRequest.getMethod())
                .endpoint(createUsageRequest.getEndpoint())
                .teamName(createUsageRequest.getTeamName())
                .categoryId(createUsageRequest.getCategoryId())
                .responseTime(createUsageRequest.getResponseTime())
                .responseCode(createUsageRequest.getResponseCode())
                .remoteAddr(createUsageRequest.getRemoteAddr())
                .build();
        elasticUsageRepository.save(usage);
    }

    @Transactional
    public void registerData() {
        Random random = new Random();
        List<Api> apis = apiRepository.findAll();
        apis.forEach(api -> {
            LocalDateTime dateTime = LocalDateTime.of(2023, 6, 1, 0, 0, 0,0);
            while (dateTime.isBefore(LocalDateTime.now().plusHours(8))) {
                int k = random.nextInt(20);
                int code = 200;
                if (api.getApiMethod().equals(ApiMethod.POST)) {
                    code = 201;
                }
                if (k == 5) {
                    code = 400;
                } else if (k == 6) {
                    code = 404;
                } else if (k == 7) {
                    code = 500;
                }

                CreateUsageRequest createUsageRequest = new CreateUsageRequest(dateTime, api.getApiMethod(),
                        api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        "3팀", api.getCategory().getId(), random.nextLong(200)+50, code, "");

                register(createUsageRequest);
                dateTime = dateTime.plusMinutes(random.nextLong(100) + 1);
            }
        });

    }

    public List<ElasticUsageResponse> findAll() {
        List<ElasticUsageResponse> usageResponses = new ArrayList<>();
        elasticUsageRepository.findAll(Sort.by("createdAt").descending()).forEach(usage -> usageResponses.add(new ElasticUsageResponse(usage)));
        return usageResponses;
    }

    @Transactional
    public void deleteAll() {
        elasticUsageRepository.deleteAll();
    }

    public List<MonthCategoryResponse> getMonthCategory(Long categoryId, Integer monthCount) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<MonthCategoryResponse> monthCategories = new ArrayList<>();
        for (int i = 0; i < monthCount; i++) {
            YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(monthCount - 1 - i));
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        yearMonth.atDay(1).atTime(0, 0, 0), yearMonth.plusMonths(1).atDay(1).atTime(0, 0, 0)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });
            monthCategories.add(new MonthCategoryResponse(yearMonth, apiCounts));
        }

        return monthCategories;
    }

    public List<MonthCategoryResponse> getMonthCategory(Long categoryId, Integer monthCount, String teamName) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<MonthCategoryResponse> monthCategories = new ArrayList<>();
        for (int i = 0; i < monthCount; i++) {
            YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(monthCount - 1 - i));
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(teamName, api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        yearMonth.atDay(1).atTime(0, 0, 0), yearMonth.plusMonths(1).atDay(1).atTime(0, 0, 0)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });
            monthCategories.add(new MonthCategoryResponse(yearMonth, apiCounts));
        }

        return monthCategories;
    }

    public List<DailyCategoryResponse> getDailyCategory(Long categoryId) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<DailyCategoryResponse> dailyCategoryResponses = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            LocalDate date = LocalDate.now().minusDays(29 - i);
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        date.atTime(0, 0, 0), date.plusDays(1).atTime(0, 0, 0)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });

            dailyCategoryResponses.add(new DailyCategoryResponse(date, apiCounts));
        }

        return dailyCategoryResponses;
    }

    public List<DailyCategoryResponse> getDailyCategory(Long categoryId, String teamName) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<DailyCategoryResponse> dailyCategoryResponses = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            LocalDate date = LocalDate.now().minusDays(29 - i);
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(teamName, api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        date.atTime(0, 0, 0), date.plusDays(1).atTime(0, 0, 0)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });

            dailyCategoryResponses.add(new DailyCategoryResponse(date, apiCounts));
        }

        return dailyCategoryResponses;
    }


    public List<HourlyCategoryResponse> getHourlyCategory(Long categoryId) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<HourlyCategoryResponse> hourlyCategoryResponses = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime date = LocalDateTime.now().minusHours(23 - i);
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        date.toLocalDate().atTime(date.getHour(), 0, 0),
                        date.toLocalDate().atTime(date.getHour(), 0, 0).plusHours(1)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });

            hourlyCategoryResponses.add(new HourlyCategoryResponse(date, apiCounts));
        }

        return hourlyCategoryResponses;
    }

    public List<HourlyCategoryResponse> getHourlyCategory(Long categoryId, String teamName) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<HourlyCategoryResponse> hourlyCategoryResponses = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime date = LocalDateTime.now().minusHours(23 - i);
            List<ApiCount> apiCounts = new ArrayList<>();
            apis.forEach(api -> {
                int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(teamName,
                        api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        date.toLocalDate().atTime(date.getHour(), 0, 0),
                        date.toLocalDate().atTime(date.getHour(), 0, 0).plusHours(1)).size();

                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });

            hourlyCategoryResponses.add(new HourlyCategoryResponse(date, apiCounts));
        }

        return hourlyCategoryResponses;
    }


    public List<MonthlyResponse> getMonthly(Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<MonthlyResponse> monthlyResponses = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(5 - i));
            int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    yearMonth.atDay(1).atTime(0, 0, 0), yearMonth.plusMonths(1).atDay(1).atTime(0, 0, 0)).size();

            monthlyResponses.add(new MonthlyResponse(yearMonth, amount));
        }

        return monthlyResponses;
    }

    public List<MonthlyResponse> getMonthly(String teamName, Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<MonthlyResponse> monthlyResponses = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(5 - i));
            int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    teamName, api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    yearMonth.atDay(1).atTime(0, 0, 0), yearMonth.plusMonths(1).atDay(1).atTime(0, 0, 0)).size();

            monthlyResponses.add(new MonthlyResponse(yearMonth, amount));
        }

        return monthlyResponses;
    }

    public List<DailyResponse> getDaily(Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<DailyResponse> dailyResponses = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            LocalDate date = LocalDate.now().minusDays(29 - i);
            int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    date.atTime(0, 0, 0), date.plusDays(1).atTime(0, 0, 0)).size();

            dailyResponses.add(new DailyResponse(date, amount));
        }

        return dailyResponses;
    }

    public List<DailyResponse> getDaily(String teamName, Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<DailyResponse> dailyResponses = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            LocalDate date = LocalDate.now().minusDays(29 - i);
            int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(teamName,
                    api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    date.atTime(0, 0, 0), date.plusDays(1).atTime(0, 0, 0)).size();

            dailyResponses.add(new DailyResponse(date, amount));
        }

        return dailyResponses;
    }

    public List<HourlyResponse> getHourly(Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<HourlyResponse> hourlyResponses = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime date = LocalDateTime.now().minusHours(23 - i);
            int amount = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    date.toLocalDate().atTime(date.getHour(), 0, 0),
                    date.toLocalDate().atTime(date.getHour(), 0, 0).plusHours(1)).size();

            hourlyResponses.add(new HourlyResponse(date, amount));
        }

        return hourlyResponses;
    }

    public List<HourlyResponse> getHourly(String teamName, Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<HourlyResponse> hourlyResponses = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime date = LocalDateTime.now().minusHours(23 - i);
            int amount = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(teamName,
                    api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    date.toLocalDate().atTime(date.getHour(), 0, 0),
                    date.toLocalDate().atTime(date.getHour(), 0, 0).plusHours(1)).size();

            hourlyResponses.add(new HourlyResponse(date, amount));
        }

        return hourlyResponses;
    }

    public List<ResponseTimeResponse> getResponseTime(Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();

        return elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        LocalDateTime.now().minusHours(24), LocalDateTime.now()).stream()
                .map(ResponseTimeResponse::new).collect(Collectors.toList());
    }

    public List<ResponseTimeResponse> getResponseTime(String teamName, Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();

        return elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        teamName, api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                        LocalDateTime.now().minusHours(24), LocalDateTime.now()).stream()
                .map(ResponseTimeResponse::new).collect(Collectors.toList());
    }

    public List<ResponseTimeCategoryResponse> getResponseTimeCategory(Long categoryId, String teamName) {
        List<Api> apis = apiRepository.findAllByCategoryId(categoryId);
        List<ResponseTimeCategoryResponse> responseTimeCategoryResponses = new ArrayList<>();

        if (teamName == null) {
            apis.forEach(api -> {
                responseTimeCategoryResponses.add(new ResponseTimeCategoryResponse(api.getId(), api.getTitle(), getResponseTime(api.getId())));
            });
        } else {
            apis.forEach(api -> {
                responseTimeCategoryResponses.add(new ResponseTimeCategoryResponse(api.getId(), api.getTitle(), getResponseTime(teamName, api.getId())));
            });
        }
        return responseTimeCategoryResponses;
    }


    public List<ResponseCodeResponse> getResponseCode(String teamName, Long apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow();
        List<ElasticUsage> usages = new ArrayList<>();
        if (teamName == null) {
            usages = elasticUsageRepository.findAllByEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    LocalDateTime.now().minusHours(24), LocalDateTime.now());
        } else {
            usages = elasticUsageRepository.findAllByTeamNameAndEndpointAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    teamName, api.getEndpoint().replace("https://k9c201.p.ssafy.io/api", ""),
                    LocalDateTime.now().minusHours(24), LocalDateTime.now());

        }
        List<ResponseCodeResponse> responseCodeResponses = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        usages.forEach(usage -> {
            int amount =  map.getOrDefault(usage.getResponseCode(), 0);
            map.put(usage.getResponseCode(), amount + 1);
        });

        map.forEach((key, value) -> {
            responseCodeResponses.add(new ResponseCodeResponse(key, value));
        });
        return responseCodeResponses;
    }

    public List<ResponseCodeCategory> getResponseCodeCategory(Long categoryId, String teamName) {
        Map<Integer, Integer> total = new HashMap<>();
        Map<Integer, Map<String, Integer>> one = new HashMap<>();
        List<ResponseCodeCategory> responseCodeCategories = new ArrayList<>();

        List<ElasticUsage> usages;
        if (teamName == null) {
            usages = elasticUsageRepository.findAllByCategoryIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    categoryId,
                    LocalDateTime.now().minusHours(24), LocalDateTime.now());
        } else {
            usages = elasticUsageRepository.findAllByTeamNameAndCategoryIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                    teamName, categoryId,
                    LocalDateTime.now().minusHours(24), LocalDateTime.now());
        }

        usages.forEach(usage -> {
            int totalAmount = total.getOrDefault(usage.getResponseCode(), 0);
            total.put(usage.getResponseCode(), totalAmount + 1);
            Map<String, Integer> endpointAmount = one.getOrDefault(usage.getResponseCode(), new HashMap<>());
            int oneAmount =  endpointAmount.getOrDefault("https://k9c201.p.ssafy.io/api" + usage.getEndpoint(), 0);
            endpointAmount.put("https://k9c201.p.ssafy.io/api" + usage.getEndpoint(), oneAmount + 1);
            one.put(usage.getResponseCode(), endpointAmount);
        });

        total.forEach((key, value) -> {
            Map<String, Integer> endpointAmount = one.get(key);
            List<ApiCount> apiCounts = new ArrayList<>();
            endpointAmount.forEach((endpoint, amount) -> {
                Api api = apiRepository.findByEndpoint(endpoint).orElseThrow();
                apiCounts.add(new ApiCount(api.getId(), api.getTitle(), amount));
            });

            responseCodeCategories.add(new ResponseCodeCategory(key, value, apiCounts));
        });

        return responseCodeCategories;
    }
}
