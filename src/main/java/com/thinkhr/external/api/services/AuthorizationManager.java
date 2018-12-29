package com.thinkhr.external.api.services;

import static com.thinkhr.external.api.ApplicationConstants.ROLE_BROKER_ADMIN;
import static com.thinkhr.external.api.ApplicationConstants.ROLE_SYSTEM_ADMIN;
import static com.thinkhr.external.api.ApplicationConstants.ROLE_THR_ADMIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.thinkhr.external.api.db.entities.ThroneRole;
import com.thinkhr.external.api.exception.APIErrorCodes;
import com.thinkhr.external.api.exception.ApplicationException;
import com.thinkhr.external.api.model.AppAuthData;

/**
 * To handle services specific to Authorization of application and user's data
 * 
 * @author Surabhi Bhawsar
 * @since 2017-12-12
 *
 */
@Service
public class AuthorizationManager extends CommonService {
    
    private static List<String> adminRoles = new ArrayList<String>(Arrays.asList(ROLE_SYSTEM_ADMIN, ROLE_THR_ADMIN, ROLE_BROKER_ADMIN));

    Map<String, ThroneRole> rolesHasJAPIAccess = new HashMap<String, ThroneRole>();

    /**
     * Purpose of this method to cover all the required authorization related stuff. 
     * 
     * @return
     */
    public boolean checkAuthorization(AppAuthData authData) {
        Integer brokerId = authData.getBrokerId();
        String roleName = authData.getRole();
        
        loadRoleMapForJAPIAccess();
        
        //Validate brokerId
        if (null == brokerId || null == companyRepository.findOne(brokerId)) {
            throw ApplicationException.createAuthorizationError(APIErrorCodes.AUTHORIZATION_FAILED, "brokerId = "+ brokerId);
        }
        //Validate user
        if (null == authData.getUser() || null == userRepository.findByUserName(authData.getUser())){
            throw ApplicationException.createAuthorizationError(APIErrorCodes.AUTHORIZATION_FAILED, "user = "+ authData.getUser());
        }
        //Validate roles
        if (StringUtils.isBlank(roleName) || !rolesHasJAPIAccess.containsKey(roleName)) {
            throw ApplicationException.createAuthorizationError(APIErrorCodes.AUTHORIZATION_FAILED, "role = "+ roleName);
        }
        
        
        return true;
    }

    /**
     * Method to load all roles from DB and put only 3 roles into map
     * those have access to APIs
     * 
     */
    private void loadRoleMapForJAPIAccess() {
        List<ThroneRole> roles = (List<ThroneRole>) throneRoleRepository.findAll();
        roles.stream().forEach(role -> {
            if (adminRoles.contains(role.getName())) {
                rolesHasJAPIAccess.put(role.getName(), role);
            }
        });
    }
}
