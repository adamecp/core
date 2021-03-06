/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.console.client.v3.stores;

import org.jboss.as.console.client.v3.dmr.AddressTemplate;
import org.jboss.dmr.client.ModelNode;
import org.jboss.gwt.circuit.Action;

import java.util.Collections;
import java.util.Map;

import static org.jboss.as.console.client.v3.stores.CrudAction.Crud.*;

/**
 * @author Harald Pehl
 */
public abstract class ReadAction implements Action {

    private final AddressTemplate addressTemplate;

    public ReadAction(AddressTemplate addressTemplate) {
        this.addressTemplate = addressTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadAction)) return false;

        ReadAction that = (ReadAction) o;
        return addressTemplate.equals(that.addressTemplate);
    }

    @Override
    public int hashCode() {
        return addressTemplate.hashCode();
    }

    public AddressTemplate getAddressTemplate() {
        return addressTemplate;
    }
}
