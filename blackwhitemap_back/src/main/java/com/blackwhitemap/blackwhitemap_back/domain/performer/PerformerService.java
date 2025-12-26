package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformerService {

    private final ChefRepository chefRepository;

    @Transactional
    public void registerChef(PerformerCommand.RegisterChef registerCommand) {
        Chef chef = Chef.of(registerCommand);
        chefRepository.registerChef(chef);
    }

    @Transactional
    public void updateChef(PerformerCommand.UpdateChef updateCommand) {
        Chef chef = chefRepository.findById(updateCommand.chefId())
                .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "셰프를 찾을 수 없습니다."));

        // 개별 필드 업데이트
        if (updateCommand.name() != null) {
            chef.updateName(updateCommand.name());
        }

        if (updateCommand.nickname() != null) {
            chef.updateNickname(updateCommand.nickname());
        }

        if (updateCommand.type() != null) {
            chef.updateType(updateCommand.type());
        }

        // Restaurant 업데이트 (내부에서 업데이트 필요 여부 판단)
        chef.updateRestaurant(
                updateCommand.restaurantName(),
                updateCommand.address(),
                updateCommand.latitude(),
                updateCommand.longitude(),
                updateCommand.closedDays(),
                updateCommand.restaurantCategory(),
                updateCommand.naverReservationUrl(),
                updateCommand.catchTableUrl(),
                updateCommand.instagramUrl()
        );

        // ChefImages 업데이트
        if (updateCommand.imageUrls() != null) {
            chef.updateImages(updateCommand.imageUrls());
        }
    }
}
