package com.borui.web.model;

import java.util.ArrayList;
import java.util.List;

public class partyLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public partyLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPartyidIsNull() {
            addCriterion("partyId is null");
            return (Criteria) this;
        }

        public Criteria andPartyidIsNotNull() {
            addCriterion("partyId is not null");
            return (Criteria) this;
        }

        public Criteria andPartyidEqualTo(Integer value) {
            addCriterion("partyId =", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidNotEqualTo(Integer value) {
            addCriterion("partyId <>", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidGreaterThan(Integer value) {
            addCriterion("partyId >", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidGreaterThanOrEqualTo(Integer value) {
            addCriterion("partyId >=", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidLessThan(Integer value) {
            addCriterion("partyId <", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidLessThanOrEqualTo(Integer value) {
            addCriterion("partyId <=", value, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidIn(List<Integer> values) {
            addCriterion("partyId in", values, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidNotIn(List<Integer> values) {
            addCriterion("partyId not in", values, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidBetween(Integer value1, Integer value2) {
            addCriterion("partyId between", value1, value2, "partyid");
            return (Criteria) this;
        }

        public Criteria andPartyidNotBetween(Integer value1, Integer value2) {
            addCriterion("partyId not between", value1, value2, "partyid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userId =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userId <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userId >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userId >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userId <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userId <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userId in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userId not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userId between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userId not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongIsNull() {
            addCriterion("createTimeLong is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongIsNotNull() {
            addCriterion("createTimeLong is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongEqualTo(Long value) {
            addCriterion("createTimeLong =", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongNotEqualTo(Long value) {
            addCriterion("createTimeLong <>", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongGreaterThan(Long value) {
            addCriterion("createTimeLong >", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongGreaterThanOrEqualTo(Long value) {
            addCriterion("createTimeLong >=", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongLessThan(Long value) {
            addCriterion("createTimeLong <", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongLessThanOrEqualTo(Long value) {
            addCriterion("createTimeLong <=", value, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongIn(List<Long> values) {
            addCriterion("createTimeLong in", values, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongNotIn(List<Long> values) {
            addCriterion("createTimeLong not in", values, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongBetween(Long value1, Long value2) {
            addCriterion("createTimeLong between", value1, value2, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andCreatetimelongNotBetween(Long value1, Long value2) {
            addCriterion("createTimeLong not between", value1, value2, "createtimelong");
            return (Criteria) this;
        }

        public Criteria andPartyownerIsNull() {
            addCriterion("partyOwner is null");
            return (Criteria) this;
        }

        public Criteria andPartyownerIsNotNull() {
            addCriterion("partyOwner is not null");
            return (Criteria) this;
        }

        public Criteria andPartyownerEqualTo(Integer value) {
            addCriterion("partyOwner =", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerNotEqualTo(Integer value) {
            addCriterion("partyOwner <>", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerGreaterThan(Integer value) {
            addCriterion("partyOwner >", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerGreaterThanOrEqualTo(Integer value) {
            addCriterion("partyOwner >=", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerLessThan(Integer value) {
            addCriterion("partyOwner <", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerLessThanOrEqualTo(Integer value) {
            addCriterion("partyOwner <=", value, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerIn(List<Integer> values) {
            addCriterion("partyOwner in", values, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerNotIn(List<Integer> values) {
            addCriterion("partyOwner not in", values, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerBetween(Integer value1, Integer value2) {
            addCriterion("partyOwner between", value1, value2, "partyowner");
            return (Criteria) this;
        }

        public Criteria andPartyownerNotBetween(Integer value1, Integer value2) {
            addCriterion("partyOwner not between", value1, value2, "partyowner");
            return (Criteria) this;
        }

        public Criteria andExt1IsNull() {
            addCriterion("ext1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("ext1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("ext1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("ext1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("ext1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("ext1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("ext1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("ext1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("ext1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("ext1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("ext1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("ext1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("ext1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("ext1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt2IsNull() {
            addCriterion("ext2 is null");
            return (Criteria) this;
        }

        public Criteria andExt2IsNotNull() {
            addCriterion("ext2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt2EqualTo(String value) {
            addCriterion("ext2 =", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotEqualTo(String value) {
            addCriterion("ext2 <>", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThan(String value) {
            addCriterion("ext2 >", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThanOrEqualTo(String value) {
            addCriterion("ext2 >=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThan(String value) {
            addCriterion("ext2 <", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThanOrEqualTo(String value) {
            addCriterion("ext2 <=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Like(String value) {
            addCriterion("ext2 like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotLike(String value) {
            addCriterion("ext2 not like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2In(List<String> values) {
            addCriterion("ext2 in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotIn(List<String> values) {
            addCriterion("ext2 not in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Between(String value1, String value2) {
            addCriterion("ext2 between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotBetween(String value1, String value2) {
            addCriterion("ext2 not between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt3IsNull() {
            addCriterion("ext3 is null");
            return (Criteria) this;
        }

        public Criteria andExt3IsNotNull() {
            addCriterion("ext3 is not null");
            return (Criteria) this;
        }

        public Criteria andExt3EqualTo(String value) {
            addCriterion("ext3 =", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotEqualTo(String value) {
            addCriterion("ext3 <>", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThan(String value) {
            addCriterion("ext3 >", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3GreaterThanOrEqualTo(String value) {
            addCriterion("ext3 >=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThan(String value) {
            addCriterion("ext3 <", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3LessThanOrEqualTo(String value) {
            addCriterion("ext3 <=", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Like(String value) {
            addCriterion("ext3 like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotLike(String value) {
            addCriterion("ext3 not like", value, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3In(List<String> values) {
            addCriterion("ext3 in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotIn(List<String> values) {
            addCriterion("ext3 not in", values, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3Between(String value1, String value2) {
            addCriterion("ext3 between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt3NotBetween(String value1, String value2) {
            addCriterion("ext3 not between", value1, value2, "ext3");
            return (Criteria) this;
        }

        public Criteria andExt4IsNull() {
            addCriterion("ext4 is null");
            return (Criteria) this;
        }

        public Criteria andExt4IsNotNull() {
            addCriterion("ext4 is not null");
            return (Criteria) this;
        }

        public Criteria andExt4EqualTo(String value) {
            addCriterion("ext4 =", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotEqualTo(String value) {
            addCriterion("ext4 <>", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThan(String value) {
            addCriterion("ext4 >", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4GreaterThanOrEqualTo(String value) {
            addCriterion("ext4 >=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThan(String value) {
            addCriterion("ext4 <", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4LessThanOrEqualTo(String value) {
            addCriterion("ext4 <=", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Like(String value) {
            addCriterion("ext4 like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotLike(String value) {
            addCriterion("ext4 not like", value, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4In(List<String> values) {
            addCriterion("ext4 in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotIn(List<String> values) {
            addCriterion("ext4 not in", values, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4Between(String value1, String value2) {
            addCriterion("ext4 between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt4NotBetween(String value1, String value2) {
            addCriterion("ext4 not between", value1, value2, "ext4");
            return (Criteria) this;
        }

        public Criteria andExt5IsNull() {
            addCriterion("ext5 is null");
            return (Criteria) this;
        }

        public Criteria andExt5IsNotNull() {
            addCriterion("ext5 is not null");
            return (Criteria) this;
        }

        public Criteria andExt5EqualTo(String value) {
            addCriterion("ext5 =", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotEqualTo(String value) {
            addCriterion("ext5 <>", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThan(String value) {
            addCriterion("ext5 >", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5GreaterThanOrEqualTo(String value) {
            addCriterion("ext5 >=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThan(String value) {
            addCriterion("ext5 <", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5LessThanOrEqualTo(String value) {
            addCriterion("ext5 <=", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Like(String value) {
            addCriterion("ext5 like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotLike(String value) {
            addCriterion("ext5 not like", value, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5In(List<String> values) {
            addCriterion("ext5 in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotIn(List<String> values) {
            addCriterion("ext5 not in", values, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5Between(String value1, String value2) {
            addCriterion("ext5 between", value1, value2, "ext5");
            return (Criteria) this;
        }

        public Criteria andExt5NotBetween(String value1, String value2) {
            addCriterion("ext5 not between", value1, value2, "ext5");
            return (Criteria) this;
        }

        public Criteria andExtint1IsNull() {
            addCriterion("extInt1 is null");
            return (Criteria) this;
        }

        public Criteria andExtint1IsNotNull() {
            addCriterion("extInt1 is not null");
            return (Criteria) this;
        }

        public Criteria andExtint1EqualTo(Integer value) {
            addCriterion("extInt1 =", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1NotEqualTo(Integer value) {
            addCriterion("extInt1 <>", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1GreaterThan(Integer value) {
            addCriterion("extInt1 >", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1GreaterThanOrEqualTo(Integer value) {
            addCriterion("extInt1 >=", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1LessThan(Integer value) {
            addCriterion("extInt1 <", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1LessThanOrEqualTo(Integer value) {
            addCriterion("extInt1 <=", value, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1In(List<Integer> values) {
            addCriterion("extInt1 in", values, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1NotIn(List<Integer> values) {
            addCriterion("extInt1 not in", values, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1Between(Integer value1, Integer value2) {
            addCriterion("extInt1 between", value1, value2, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint1NotBetween(Integer value1, Integer value2) {
            addCriterion("extInt1 not between", value1, value2, "extint1");
            return (Criteria) this;
        }

        public Criteria andExtint2IsNull() {
            addCriterion("extInt2 is null");
            return (Criteria) this;
        }

        public Criteria andExtint2IsNotNull() {
            addCriterion("extInt2 is not null");
            return (Criteria) this;
        }

        public Criteria andExtint2EqualTo(Integer value) {
            addCriterion("extInt2 =", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2NotEqualTo(Integer value) {
            addCriterion("extInt2 <>", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2GreaterThan(Integer value) {
            addCriterion("extInt2 >", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2GreaterThanOrEqualTo(Integer value) {
            addCriterion("extInt2 >=", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2LessThan(Integer value) {
            addCriterion("extInt2 <", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2LessThanOrEqualTo(Integer value) {
            addCriterion("extInt2 <=", value, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2In(List<Integer> values) {
            addCriterion("extInt2 in", values, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2NotIn(List<Integer> values) {
            addCriterion("extInt2 not in", values, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2Between(Integer value1, Integer value2) {
            addCriterion("extInt2 between", value1, value2, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint2NotBetween(Integer value1, Integer value2) {
            addCriterion("extInt2 not between", value1, value2, "extint2");
            return (Criteria) this;
        }

        public Criteria andExtint3IsNull() {
            addCriterion("extInt3 is null");
            return (Criteria) this;
        }

        public Criteria andExtint3IsNotNull() {
            addCriterion("extInt3 is not null");
            return (Criteria) this;
        }

        public Criteria andExtint3EqualTo(Integer value) {
            addCriterion("extInt3 =", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3NotEqualTo(Integer value) {
            addCriterion("extInt3 <>", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3GreaterThan(Integer value) {
            addCriterion("extInt3 >", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3GreaterThanOrEqualTo(Integer value) {
            addCriterion("extInt3 >=", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3LessThan(Integer value) {
            addCriterion("extInt3 <", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3LessThanOrEqualTo(Integer value) {
            addCriterion("extInt3 <=", value, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3In(List<Integer> values) {
            addCriterion("extInt3 in", values, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3NotIn(List<Integer> values) {
            addCriterion("extInt3 not in", values, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3Between(Integer value1, Integer value2) {
            addCriterion("extInt3 between", value1, value2, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint3NotBetween(Integer value1, Integer value2) {
            addCriterion("extInt3 not between", value1, value2, "extint3");
            return (Criteria) this;
        }

        public Criteria andExtint4IsNull() {
            addCriterion("extInt4 is null");
            return (Criteria) this;
        }

        public Criteria andExtint4IsNotNull() {
            addCriterion("extInt4 is not null");
            return (Criteria) this;
        }

        public Criteria andExtint4EqualTo(Integer value) {
            addCriterion("extInt4 =", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4NotEqualTo(Integer value) {
            addCriterion("extInt4 <>", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4GreaterThan(Integer value) {
            addCriterion("extInt4 >", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4GreaterThanOrEqualTo(Integer value) {
            addCriterion("extInt4 >=", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4LessThan(Integer value) {
            addCriterion("extInt4 <", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4LessThanOrEqualTo(Integer value) {
            addCriterion("extInt4 <=", value, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4In(List<Integer> values) {
            addCriterion("extInt4 in", values, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4NotIn(List<Integer> values) {
            addCriterion("extInt4 not in", values, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4Between(Integer value1, Integer value2) {
            addCriterion("extInt4 between", value1, value2, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint4NotBetween(Integer value1, Integer value2) {
            addCriterion("extInt4 not between", value1, value2, "extint4");
            return (Criteria) this;
        }

        public Criteria andExtint5IsNull() {
            addCriterion("extInt5 is null");
            return (Criteria) this;
        }

        public Criteria andExtint5IsNotNull() {
            addCriterion("extInt5 is not null");
            return (Criteria) this;
        }

        public Criteria andExtint5EqualTo(Integer value) {
            addCriterion("extInt5 =", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5NotEqualTo(Integer value) {
            addCriterion("extInt5 <>", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5GreaterThan(Integer value) {
            addCriterion("extInt5 >", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5GreaterThanOrEqualTo(Integer value) {
            addCriterion("extInt5 >=", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5LessThan(Integer value) {
            addCriterion("extInt5 <", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5LessThanOrEqualTo(Integer value) {
            addCriterion("extInt5 <=", value, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5In(List<Integer> values) {
            addCriterion("extInt5 in", values, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5NotIn(List<Integer> values) {
            addCriterion("extInt5 not in", values, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5Between(Integer value1, Integer value2) {
            addCriterion("extInt5 between", value1, value2, "extint5");
            return (Criteria) this;
        }

        public Criteria andExtint5NotBetween(Integer value1, Integer value2) {
            addCriterion("extInt5 not between", value1, value2, "extint5");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}