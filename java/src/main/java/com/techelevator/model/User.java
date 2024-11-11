package com.techelevator.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

   private int id;
   private String username;
   private String password;
   private boolean restricted;
   private String displayName;
   private int violations;
   private int avatarId;
   private Set<Authority> authorities = new HashSet<>();

   public User() { }

   public User(int id, String username, String password, String email, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.displayName = displayName;
      if (authorities != null) this.setAuthorities(authorities);
   }

   public boolean isRestricted() {
      return restricted;
   }

   public int getAvatarId() {
      return avatarId;
   }

   public void setAvatarId(int avatarId) {
      this.avatarId = avatarId;
   }

   public void setRestricted(boolean restricted) {
      this.restricted = restricted;
   }

   public String getDisplayName() {
      return displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public int getViolations() {
      return violations;
   }

   public void setViolations(int violations) {
      this.violations = violations;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", authorities=" + authorities +
              '}';
   }
}
