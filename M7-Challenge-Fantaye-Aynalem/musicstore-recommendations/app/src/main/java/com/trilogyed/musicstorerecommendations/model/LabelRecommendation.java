package com.trilogyed.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "label_recommendation")
public class LabelRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_recommendation_id")
    private Long labelRecommendationId;
    @Column(name = "label_id")
    private int labelId;
    @Column(name = "user_id")
    private int userId;
    private boolean liked;

    public LabelRecommendation() {
    }

    public LabelRecommendation(Long labelRecommendationId,int labelId,int userId,boolean liked) {
        this.labelRecommendationId = labelRecommendationId;
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public LabelRecommendation(int labelId,int userId,boolean liked) {
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public Long getLabelRecommendationId() {
        return labelRecommendationId;
    }

    public void setLabelRecommendationId(Long labelRecommendationId) {
        this.labelRecommendationId = labelRecommendationId;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelRecommendation)) return false;
        LabelRecommendation that = (LabelRecommendation) o;
        return getLabelId() == that.getLabelId() && getUserId() == that.getUserId() && isLiked() == that.isLiked() && Objects.equals(getLabelRecommendationId(),that.getLabelRecommendationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabelRecommendationId(),getLabelId(),getUserId(),isLiked());
    }

    @Override
    public String toString() {
        return "LabelRecommendation{" +
                "labelRecommendationId=" + labelRecommendationId +
                ", labelId=" + labelId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
