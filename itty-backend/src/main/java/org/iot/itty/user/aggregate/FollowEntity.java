package org.iot.itty.user.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FollowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_code_pk")
	private Integer followCodePk;

	@Column(name = "follower_code_fk")
	private Integer followerCodeFk;

	@Column(name = "followee_code_fk")
	private Integer followeeCodeFk;
}
