//
//  SMAnnotation.h
//  smartMenu
//
//  Created by 孙翔宇 on 16/4/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MapKit/MapKit.h>

@class Venue;

@interface SMAnnotation : NSObject <MKAnnotation>

@property (nonatomic, assign) CLLocationCoordinate2D coordinate;
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *subtitle;
@property (nonatomic, copy) NSString *icon;
@property(nonatomic, strong) Venue *venue;

@end
