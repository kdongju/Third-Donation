package com.thirdlife.thirddonation.db.user.entity;

import com.thirdlife.thirddonation.db.nft.entity.Sales;
import com.thirdlife.thirddonation.db.nft.entity.Wish;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User 엔티티.
 * id는 DB 에서 생성되는 값입니다.
 */
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 256)
    private String walletAddress;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dateCreated;

    private LocalDateTime dateExchanged;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(length = 300)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_img_id")
    private UserImg userImg;

    @Column
    private Integer followerCount;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Follow> follows = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Wish> wishes = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Sales> sellNfts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Sales> buyNfts = new ArrayList<>();

    @Formula("(select count(*) from follow f where f.following_id = id)")
    private Long followCount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (int i = this.authority.ordinal(); i > -1; i--) {
            authorities.add(new SimpleGrantedAuthority(Authority.values()[i].name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void changeUserImg(UserImg userImg) {
        this.userImg = userImg;
    }

}
